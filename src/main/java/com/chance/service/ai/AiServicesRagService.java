package com.chance.service.ai;

import com.chance.component.ai.ModelFactory;
import com.chance.component.ai.MultiDocumentLoader;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于AiServices的RAG服务
 * 将RAG功能封装为简洁的接口调用
 *
 * @author chance
 * @date 2026/5/4 15:30
 * @since 1.0
 */
@Slf4j
@Service
public class AiServicesRagService {

    private final ChatLanguageModel chatModel;
    private final MultiDocumentLoader multiDocumentLoader;
    private RagAssistant ragAssistant;

    private EmbeddingStore<TextSegment> store;
    private EmbeddingModel embeddingModel;

    @Value("${rag.chunkSize:300}")
    private int chunkSize;

    @Value("${rag.chunkOverlap:30}")
    private int chunkOverlap;

    @Value("${rag.maxRelevant:3}")
    private int maxRelevant;

    @Value("${rag.similarityThreshold:0.6}")
    private double similarityThreshold;

    @Value("${rag.topKMultiplier:2}")
    private int topKMultiplier;

    @Value("${rag.knowledgeBasePath:docs}")
    private String knowledgeBasePath;

    public AiServicesRagService(ModelFactory modelFactory, MultiDocumentLoader multiDocumentLoader) {
        this.chatModel = modelFactory.getModel();
        this.multiDocumentLoader = multiDocumentLoader;
    }

    /**
     * 初始化：创建AiServices实例并加载知识库
     */
    @PostConstruct
    public void init() {
        log.info("初始化AiServices RAG服务");

        // 1. 创建RagAssistant实例（使用AiServices）
        ragAssistant = AiServices.builder(RagAssistant.class)
                .chatLanguageModel(chatModel)
                .build();

        log.info("RagAssistant创建完成");

        // 2. 初始化向量库和嵌入模型
        embeddingModel = new AllMiniLmL6V2EmbeddingModel();
        store = new InMemoryEmbeddingStore<>();

        // 3. 加载文档
        loadKnowledgeBase();
    }

    /**
     * 加载知识库（支持多文档格式）
     */
    private void loadKnowledgeBase() {
        log.info("开始加载知识库文档，路径: {}", knowledgeBasePath);

        // 使用多文档加载器，支持PDF、Word、TXT等格式
        List<TextSegment> segments = multiDocumentLoader.loadFromClasspath(
                knowledgeBasePath, chunkSize, chunkOverlap);

        log.info("文档分段完成，共 {} 个片段", segments.size());

        if (segments.isEmpty()) {
            log.warn("未加载到任何文档片段，请检查知识库路径: {}", knowledgeBasePath);
            return;
        }

        // 向量化并存入向量库
        segments.forEach(seg -> {
            Embedding embedding = embeddingModel.embed(seg).content();
            store.add(embedding, seg);
        });

        log.info("知识库加载完成，已向量化存储 {} 个片段", segments.size());
    }

    /**
     * 简化的问答接口 - 自动处理检索和生成
     *
     * @param question 用户问题
     * @return AI生成的回答
     */
    public String ask(String question) {
        log.debug("收到问题: {}", question);

        // 1. 检索相关上下文
        String context = retrieveContext(question);

        if (context == null || context.isEmpty()) {
            log.warn("未找到相关知识片段");
            return "抱歉，我在知识库中没有找到相关信息。";
        }

        log.debug("检索到相关上下文，长度: {} 字符", context.length());

        // 2. 使用AiServices生成回答
        try {
            String answer = ragAssistant.answer(context, question);
            log.debug("生成回答完成");
            return answer;
        } catch (Exception e) {
            log.error("调用AI服务失败", e);
            return "抱歉，处理您的问题时出现了错误，请稍后重试。";
        }
    }

    /**
     * 检索相关上下文
     *
     * @param question 用户问题
     * @return 格式化的上下文字符串
     */
    private String retrieveContext(String question) {
        // 1. 向量化查询
        Embedding queryEmbedding = embeddingModel.embed(question).content();

        // 2. 优化的检索策略
        List<EmbeddingMatch<TextSegment>> matches = retrieveWithOptimization(queryEmbedding);

        if (matches.isEmpty()) {
            return null;
        }

        // 3. 格式化上下文
        return formatContext(matches);
    }

    /**
     * 优化的检索策略：TopK + 阈值过滤
     */
    private List<EmbeddingMatch<TextSegment>> retrieveWithOptimization(Embedding queryEmbedding) {
        // Step 1: 扩大检索范围
        int candidateCount = maxRelevant * topKMultiplier;
        List<EmbeddingMatch<TextSegment>> candidates = store.findRelevant(queryEmbedding, candidateCount);

        log.debug("初始检索到 {} 个候选片段", candidates.size());

        if (candidates.isEmpty()) {
            return candidates;
        }

        // Step 2: 按相似度阈值过滤
        List<EmbeddingMatch<TextSegment>> filtered = candidates.stream()
                .filter(match -> match.score() >= similarityThreshold)
                .collect(Collectors.toList());

        log.debug("阈值过滤后剩余 {} 个片段（阈值: {}）", filtered.size(), similarityThreshold);

        // Step 3: 返回前maxRelevant个结果
        return filtered.stream()
                .limit(maxRelevant)
                .collect(Collectors.toList());
    }

    /**
     * 格式化上下文信息
     */
    private String formatContext(List<EmbeddingMatch<TextSegment>> matches) {
        StringBuilder context = new StringBuilder();

        for (int i = 0; i < matches.size(); i++) {
            TextSegment segment = matches.get(i).embedded();
            context.append(String.format("[%d] %s\n", i + 1, segment.text()));
        }

        return context.toString().trim();
    }

    /**
     * 直接使用RagAssistant的简化接口（不经过检索）
     * 适用于已有上下文的场景
     *
     * @param question 用户问题
     * @return AI生成的回答
     */
    public String askDirect(String question) {
        log.debug("直接提问: {}", question);

        try {
            return ragAssistant.ask(question);
        } catch (Exception e) {
            log.error("调用AI服务失败", e);
            return "抱歉，处理您的问题时出现了错误。";
        }
    }

    /**
     * 获取RagAssistant实例（供高级使用）
     *
     * @return RagAssistant实例
     */
    public RagAssistant getRagAssistant() {
        return ragAssistant;
    }
}
