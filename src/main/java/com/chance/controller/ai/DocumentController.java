package com.chance.controller.ai;

import com.chance.component.ai.MultiDocumentLoader;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * 文档管理控制器
 * 支持上传和管理PDF、Word等文档
 *
 * @author chance
 * @date 2026/5/4 15:45
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/document")
@Tag(name = "文档管理", description = "上传和管理知识库文档")
public class DocumentController {

    @Resource
    private MultiDocumentLoader multiDocumentLoader;

    private static final String UPLOAD_DIR = "src/main/resources/docs/uploads/";

    /**
     * 上传文档
     *
     * @param file 上传的文件
     * @return 上传结果
     */
    @PostMapping("/upload")
    @Operation(summary = "上传文档", description = "支持PDF、Word(.docx)、TXT格式")
    public Map<String, Object> uploadDocument(@RequestParam("file") MultipartFile file) {
        log.info("收到文档上传请求: {}", file.getOriginalFilename());

        Map<String, Object> result = new HashMap<>();

        try {
            // 验证文件格式
            String fileName = file.getOriginalFilename();
            if (!isSupportedFormat(fileName)) {
                result.put("success", false);
                result.put("message", "不支持的文件格式。支持的格式: PDF, DOCX, DOC, TXT");
                return result;
            }

            // 创建上传目录
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 保存文件
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.write(filePath, file.getBytes());

            log.info("文档上传成功: {}, 大小: {} bytes", fileName, file.getSize());

            result.put("success", true);
            result.put("message", "文档上传成功");
            result.put("fileName", fileName);
            result.put("fileSize", file.getSize());
            result.put("supportedFormats", multiDocumentLoader.getSupportedFormats());

        } catch (IOException e) {
            log.error("文档上传失败", e);
            result.put("success", false);
            result.put("message", "文档上传失败: " + e.getMessage());
        }

        return result;
    }

    /**
     * 批量上传文档
     *
     * @param files 上传的多个文件
     * @return 上传结果
     */
    @PostMapping("/upload-batch")
    @Operation(summary = "批量上传文档", description = "一次上传多个文档")
    public Map<String, Object> uploadBatchDocuments(@RequestParam("files") MultipartFile[] files) {
        log.info("收到批量文档上传请求，文件数: {}", files.length);

        Map<String, Object> result = new HashMap<>();
        int successCount = 0;
        int failCount = 0;

        for (MultipartFile file : files) {
            try {
                String fileName = file.getOriginalFilename();
                if (!isSupportedFormat(fileName)) {
                    log.warn("跳过不支持的文件格式: {}", fileName);
                    failCount++;
                    continue;
                }

                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                Files.write(filePath, file.getBytes());
                successCount++;
                log.info("上传成功: {}", fileName);

            } catch (IOException e) {
                log.error("上传失败: {}", file.getOriginalFilename(), e);
                failCount++;
            }
        }

        result.put("success", true);
        result.put("total", files.length);
        result.put("successCount", successCount);
        result.put("failCount", failCount);
        result.put("message", String.format("上传完成: 成功 %d, 失败 %d", successCount, failCount));

        return result;
    }

    /**
     * 获取支持的文档格式
     *
     * @return 支持的格式列表
     */
    @GetMapping("/supported-formats")
    @Operation(summary = "获取支持的文档格式", description = "返回所有支持的文档格式")
    public Map<String, Object> getSupportedFormats() {
        Map<String, Object> result = new HashMap<>();
        result.put("formats", multiDocumentLoader.getSupportedFormats());

        Map<String, String> descriptions = new HashMap<>();
        descriptions.put(".pdf", "PDF文档");
        descriptions.put(".docx", "Word文档（新版）");
        descriptions.put(".doc", "Word文档（旧版）");
        descriptions.put(".txt", "纯文本文件");
        result.put("descriptions", descriptions);

        return result;
    }

    /**
     * 判断是否为支持的格式
     */
    private boolean isSupportedFormat(String fileName) {
        if (fileName == null) {
            return false;
        }
        String lowerName = fileName.toLowerCase();
        return lowerName.endsWith(".pdf")
                || lowerName.endsWith(".docx")
                || lowerName.endsWith(".doc")
                || lowerName.endsWith(".txt");
    }
}
