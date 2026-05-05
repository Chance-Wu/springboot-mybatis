package com.chance.service.ai;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档加载器 - 支持智能分段切块
 *
 * @author chance
 * @date 2026/5/4 14:47
 * @since 1.0
 */
public class DocumentLoader {

    /**
     * 加载文档并进行智能分段切块
     *
     * @param path         文件路径（classpath相对路径）
     * @param chunkSize    每个片段的大小（字符数），建议300-500
     * @param chunkOverlap 片段重叠大小（字符数），建议30-50，保持上下文连贯性
     * @return 分段后的文本片段列表
     */
    public static List<TextSegment> load(String path, int chunkSize, int chunkOverlap) {

        try {
            InputStream is = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(path);

            if (is == null) {
                throw new RuntimeException("文件不存在: " + path);
            }

            // 读取整个文件内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String text = reader.lines().collect(Collectors.joining("\n"));

            // 创建Document对象
            Document document = Document.from(text);

            // 使用递归分割器进行智能分段
            // 递归分割器会尝试按段落、句子、单词等层级依次分割，保留语义完整性
            return DocumentSplitters.recursive(chunkSize, chunkOverlap).split(document);

        } catch (Exception e) {
            throw new RuntimeException("加载文档失败: " + path, e);
        }
    }

    /**
     * 加载文档并使用默认参数分段（chunkSize=300, overlap=30）
     *
     * @param path 文件路径（classpath相对路径）
     * @return 分段后的文本片段列表
     */
    public static List<TextSegment> load(String path) {
        return load(path, 300, 30);
    }
}
