package com.chance.designpatterns.adapter.clazz;

/**
 * 类适配器
 * 该类通过继承{@link LegacyFileProcessor}并实现{@link FileProcessor}接口，充当适配器的角色
 * 其目的是使现有的LegacyFileProcessor能够以新的接口FileProcessor被使用，而无需修改其内部逻辑
 * 这种设计模式常用于当希望将一个类的接口转换成客户端所期待的另一个接口时
 *
 * @author chance
 * @date 2024/12/5 15:29
 * @since 1.0
 */
public class FileProcessorAdapter extends LegacyFileProcessor implements FileProcessor {

    /**
     * 处理文件
     * 该方法覆写自FileProcessor接口，旨在通过调用旧的API来适配新的接口
     * 首先调用readFile方法读取文件内容，然后通过processContent方法处理内容
     * 这种方式使得旧的文件处理逻辑无需改动，即可适配新的文件处理接口
     *
     * @param filePath 文件路径，指定需要处理的文件位置
     */
    @Override
    public void processFile(String filePath) {
        readFile(filePath);
        processContent();
    }
}
