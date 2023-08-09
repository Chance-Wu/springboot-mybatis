package com.chance.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <p> 自定义资源加载组件 </p>
 *
 * @author chance
 * @date 2023/8/9 14:52
 * @since 1.0
 */
@Component
public class CustomResourceLoader implements ResourceLoaderAware {

    private static final Logger logger = LoggerFactory.getLogger(CustomResourceLoader.class);

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 获取资源文件流
     *
     * @param path
     * @return
     * @throws IOException
     */
    public InputStream getInputStream(String path) throws IOException {
        Resource banner = resourceLoader.getResource(path);
        return banner.getInputStream();
    }

    public void showResourceData(String path) throws IOException {
        Resource banner = resourceLoader.getResource(path);
        InputStream in = banner.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            logger.info(line);
        }
        reader.close();
    }
}