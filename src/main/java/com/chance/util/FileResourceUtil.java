package com.chance.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 获取文件资源工具类
 *
 * @author: chance
 * @date: 2024/9/20 16:47
 * @since: 1.0
 */
@Component
public class FileResourceUtil {

    private final ResourceLoader resourceLoader;

    public FileResourceUtil(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 从类路径中获取文件内容
     *
     * @param classpath 文件在类路径中的路径
     * @return 文件内容字符串
     * @throws IOException
     */
    public String getFileContentFromClasspath(String classpath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + classpath);
        return new String(Files.readAllBytes(Paths.get(resource.getURI())), StandardCharsets.UTF_8);
    }

    /**
     * 从文件系统中获取文件内容
     *
     * @param filePath 文件系统中的路径
     * @return 文件内容字符串
     * @throws IOException
     */
    public String getFileContentFromFileSystem(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource("file:" + filePath);
        return new String(Files.readAllBytes(Paths.get(resource.getURI())), StandardCharsets.UTF_8);
    }

    /**
     * 从 URL 中获取文件内容
     *
     * @param url URL 地址
     * @return 文件内容字符串
     * @throws IOException
     */
    public String getFileContentFromUrl(String url) throws IOException {
        Resource resource = resourceLoader.getResource(url);
        return new String(Files.readAllBytes(Paths.get(resource.getURI())), StandardCharsets.UTF_8);
    }
}

