package com.chance.service.ai;

import com.chance.component.ai.ModelFactory;
import com.chance.component.ai.RagPromptConfig;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * RAG服务 - 检索增强生成
 * 支持智能文档分段切块，提高检索准确率
 *
 * @author chance
 * @date 2026/5/4 14:39
 * @since 1.0
 */
@Slf4j
@Service
public class RagService {

    private final ChatLanguageModel chatModel;
    private final RagPromptConfig promptConfig;
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

    public RagService(ModelFactory modelFactory, RagPromptConfig promptConfig) {
        this.chatModel = modelFactory.getModel();
        this.promptConfig = promptConfig;
        // 注意：不在构造函数中调用 init()，因为此时 @Value 还未注入
    }

    /**
     * 初始化RAG服务
     * 使用 @PostConstruct 确保在所有依赖注入完成后执行
     */
    @PostConstruct
    private void init() {
        log.info("初始化RAG服务 - chunkSize: {}, chunkOverlap: {}, maxRelevant: {}, similarityThreshold: {}",
                chunkSize, chunkOverlap, maxRelevant, similarityThreshold);

        // 1️⃣ embedding模型（本地All-MiniLM-L6-v2）
        embeddingModel = new AllMiniLmL6V2EmbeddingModel();

        // 2️⃣ 内存向量库
        store = new InMemoryEmbeddingStore<>();

        // 3️⃣ 加载文档并进行智能分段切块
        List<TextSegment> segments = DocumentLoader.load("docs/knowledge.txt", chunkSize, chunkOverlap);
        log.info("加载文档完成，共 {} 个文本片段", segments.size());

        // 4️⃣ 向量化 + 入库
        segments.forEach(seg -> {
            Embedding embedding = embeddingModel.embed(seg).content();
            store.add(embedding, seg);
        });

        log.info("文档向量化完成，已存入向量库");
    }

    /**
     * 基于知识库回答问题
     *
     * @param question 用户问题
     * @return AI生成的回答
     */
    public String ask(String question) {
        log.debug("收到问题: {}", question);

        // 1️⃣ 查询向量
        Embedding queryEmbedding = embeddingModel.embed(question).content();

        // 2️⃣ 检索最相关内容（使用优化的检索策略）
        List<EmbeddingMatch<TextSegment>> matches = retrieveWithOptimization(queryEmbedding);

        if (matches.isEmpty()) {
            log.warn("未找到相关知识片段（相似度阈值: {}）", similarityThreshold);
            return "抱歉，我在知识库中没有找到相关信息。";
        }

        log.debug("检索到 {} 个相关片段（经过阈值过滤）", matches.size());

        // 3️⃣ 构建标准RAG Prompt
        String prompt = buildRagPrompt(question, matches);

        // 4️⃣ 调用LLM生成回答
        String answer = chatModel.generate(prompt);
        log.debug("生成回答完成");

        return answer;
    }

    /**
     * 优化的检索策略：TopK + 阈值过滤
     * 1. 先检索更多的候选结果（topKMultiplier倍）
     * 2. 按相似度分数过滤掉低质量结果
     * 3. 返回最终的前maxRelevant个结果
     *
     * @param queryEmbedding 查询向量
     * @return 过滤后的相关片段列表
     */
    private List<EmbeddingMatch<TextSegment>> retrieveWithOptimization(Embedding queryEmbedding) {
        // Step 1: 扩大检索范围，获取更多候选结果
        int candidateCount = maxRelevant * topKMultiplier;
        List<EmbeddingMatch<TextSegment>> candidates = store.findRelevant(queryEmbedding, candidateCount);

        log.debug("初始检索到 {} 个候选片段", candidates.size());

        if (candidates.isEmpty()) {
            return candidates;
        }

        // Step 2: 按相似度阈值过滤
        List<EmbeddingMatch<TextSegment>> filtered = candidates.stream()
                .filter(match -> match.score() >= similarityThreshold)
                .collect(java.util.stream.Collectors.toList());

        log.debug("阈值过滤后剩余 {} 个片段（阈值: {}）", filtered.size(), similarityThreshold);

        // Step 3: 返回前maxRelevant个结果（已经是按相似度排序的）
        return filtered.stream()
                .limit(maxRelevant)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 构建标准RAG Prompt
     * 采用业界最佳实践，提高回答质量
     *
     * @param question 用户问题
     * @param matches  检索到的相关片段
     * @return 格式化后的Prompt
     */
    private String buildRagPrompt(String question, List<EmbeddingMatch<TextSegment>> matches) {
        StringBuilder prompt = new StringBuilder();

        // 系统指令 - 定义AI的角色和行为准则
        prompt.append(promptConfig.getSystemInstruction()).append("\n\n");

        // 约束条件 - 明确回答的规则
        prompt.append("【回答规则】\n");
        prompt.append(promptConfig.getFormattedRules());
        prompt.append("\n");

        // 上下文信息 - 格式化的知识片段
        prompt.append(promptConfig.getContextTitle()).append("\n");
        for (int i = 0; i < matches.size(); i++) {
            TextSegment segment = matches.get(i).embedded();
            if (promptConfig.isShowSegmentNumber()) {
                prompt.append(String.format("[%d] %s%s", i + 1, segment.text(), promptConfig.getSegmentSeparator()));
            } else {
                prompt.append(segment.text()).append(promptConfig.getSegmentSeparator());
            }
        }
        prompt.append("\n");

        // 用户问题
        prompt.append(promptConfig.getQuestionTitle()).append("\n");
        prompt.append(question);
        prompt.append("\n\n");

        // 回答引导
        prompt.append(promptConfig.getAnswerTitle()).append("\n");

        return prompt.toString();
    }
}
