package com.chance.component.ai;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.parser.apache.poi.ApachePoiDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 多文档加载器
 * 支持PDF、Word(.docx)、TXT等多种格式
 *
 * @author chance
 * @date 2026/5/4 15:40
 * @since 1.0
 */
@Slf4j
@Component
public class MultiDocumentLoader {

    private final ApachePdfBoxDocumentParser pdfParser = new ApachePdfBoxDocumentParser();
    private final ApachePoiDocumentParser wordParser = new ApachePoiDocumentParser();

    /**
     * 从目录加载所有支持的文档
     *
     * @param directoryPath 目录路径（classpath相对路径）
     * @return 所有文档的文本片段列表
     */
    public List<TextSegment> loadFromDirectory(String directoryPath) {
        log.info("开始从目录加载文档: {}", directoryPath);

        List<TextSegment> allSegments = new ArrayList<>();

        try {
            // 获取classpath下的目录路径
            String fullPath = getClass().getClassLoader().getResource(directoryPath).getPath();
            Path dirPath = Paths.get(fullPath);

            if (!Files.exists(dirPath)) {
                log.warn("目录不存在: {}", fullPath);
                return allSegments;
            }

            // 遍历目录下的所有文件
            try (Stream<Path> paths = Files.walk(dirPath, 1)) {
                List<Path> files = paths
                        .filter(Files::isRegularFile)
                        .filter(this::isSupportedFile)
                        .collect(java.util.stream.Collectors.toList());

                log.info("找到 {} 个支持的文档", files.size());

                for (Path filePath : files) {
                    try {
                        List<TextSegment> segments = loadSingleFile(filePath);
                        allSegments.addAll(segments);
                        log.info("成功加载文档: {}, 片段数: {}",
                                filePath.getFileName(), segments.size());
                    } catch (Exception e) {
                        log.error("加载文档失败: {}", filePath.getFileName(), e);
                    }
                }
            }

            log.info("文档加载完成，共 {} 个文本片段", allSegments.size());

        } catch (Exception e) {
            log.error("加载目录失败: {}", directoryPath, e);
            throw new RuntimeException("加载目录失败: " + directoryPath, e);
        }

        return allSegments;
    }

    /**
     * 加载单个文件
     *
     * @param filePath 文件路径
     * @return 文本片段列表
     */
    public List<TextSegment> loadSingleFile(Path filePath) throws Exception {
        String fileName = filePath.getFileName().toString().toLowerCase();

        log.debug("加载文件: {}", fileName);

        Document document;
        try (InputStream inputStream = Files.newInputStream(filePath)) {
            if (fileName.endsWith(".pdf")) {
                document = pdfParser.parse(inputStream);
            } else if (fileName.endsWith(".docx") || fileName.endsWith(".doc")) {
                document = wordParser.parse(inputStream);
            } else if (fileName.endsWith(".txt")) {
                document = loadTextFile(inputStream);
            } else {
                throw new UnsupportedOperationException("不支持的文件格式: " + fileName);
            }
        }

        // 使用递归分割器进行分段
        return DocumentSplitters.recursive(300, 30).split(document);
    }

    /**
     * 从classpath加载多个文档
     *
     * @param directoryPath 目录路径
     * @param chunkSize     片段大小
     * @param chunkOverlap  片段重叠
     * @return 文本片段列表
     */
    public List<TextSegment> loadFromClasspath(String directoryPath, int chunkSize, int chunkOverlap) {
        log.info("从classpath加载文档: {}, chunkSize: {}, chunkOverlap: {}",
                directoryPath, chunkSize, chunkOverlap);

        List<TextSegment> allSegments = new ArrayList<>();

        try {
            String fullPath = getClass().getClassLoader().getResource(directoryPath).getPath();
            Path dirPath = Paths.get(fullPath);

            if (!Files.exists(dirPath)) {
                log.warn("目录不存在: {}", fullPath);
                return allSegments;
            }

            try (Stream<Path> paths = Files.walk(dirPath, 1)) {
                List<Path> files = paths
                        .filter(Files::isRegularFile)
                        .filter(this::isSupportedFile)
                        .collect(java.util.stream.Collectors.toList());

                log.info("找到 {} 个支持的文档", files.size());

                for (Path filePath : files) {
                    try {
                        Document document = parseFile(filePath);
                        List<TextSegment> segments = DocumentSplitters.recursive(chunkSize, chunkOverlap)
                                .split(document);
                        allSegments.addAll(segments);
                        log.info("成功加载: {}, 片段数: {}", filePath.getFileName(), segments.size());
                    } catch (Exception e) {
                        log.error("加载文档失败: {}", filePath.getFileName(), e);
                    }
                }
            }

            log.info("文档加载完成，共 {} 个文本片段", allSegments.size());

        } catch (Exception e) {
            log.error("加载文档失败: {}", directoryPath, e);
            throw new RuntimeException("加载文档失败: " + directoryPath, e);
        }

        return allSegments;
    }

    /**
     * 解析文件为Document对象
     */
    private Document parseFile(Path filePath) throws Exception {
        String fileName = filePath.getFileName().toString().toLowerCase();

        try (InputStream inputStream = Files.newInputStream(filePath)) {
            if (fileName.endsWith(".pdf")) {
                return pdfParser.parse(inputStream);
            } else if (fileName.endsWith(".docx") || fileName.endsWith(".doc")) {
                return wordParser.parse(inputStream);
            } else if (fileName.endsWith(".txt")) {
                return loadTextFile(inputStream);
            } else {
                throw new UnsupportedOperationException("不支持的文件格式: " + fileName);
            }
        }
    }

    /**
     * 加载TXT文件
     */
    private Document loadTextFile(InputStream inputStream) throws Exception {
        java.util.Scanner scanner = new java.util.Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        String content = scanner.hasNext() ? scanner.next() : "";
        scanner.close();
        return Document.from(content);
    }

    /**
     * 判断是否为支持的文件格式
     */
    private boolean isSupportedFile(Path path) {
        String fileName = path.getFileName().toString().toLowerCase();
        return fileName.endsWith(".pdf")
                || fileName.endsWith(".docx")
                || fileName.endsWith(".doc")
                || fileName.endsWith(".txt");
    }

    /**
     * 获取支持的文件格式列表
     */
    public List<String> getSupportedFormats() {
        return java.util.Arrays.asList(".pdf", ".docx", ".doc", ".txt");
    }
}
