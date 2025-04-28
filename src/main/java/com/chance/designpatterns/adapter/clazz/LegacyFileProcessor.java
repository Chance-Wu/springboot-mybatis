package com.chance.designpatterns.adapter.clazz;

/**
 * 旧文件处理类
 * <p>该类提供了读取和处理文件内容的方法
 * 主要用于处理旧格式的文件
 *
 * @author chance
 * @date 2024/12/5 15:25
 * @since 1.0
 */
public class LegacyFileProcessor {

    /**
     * 读取文件
     *
     * @param fileName 文件名，用于指定需要读取的文件
     */
    public void readFile(String fileName) {
        System.out.println("Reading file: " + fileName);
    }

    /**
     * 处理内容
     * 该方法用于处理文件内容，执行必要的转换或计算
     */
    public void processContent() {
        System.out.println("Processing content...");
    }
}
