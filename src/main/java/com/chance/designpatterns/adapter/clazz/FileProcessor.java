package com.chance.designpatterns.adapter.clazz;

/**
 * 新的文件处理接口
 * <p>该类通过继承{@link LegacyFileProcessor}并实现{@link FileProcessor}接口，充当适配器的角色
 * 其目的是使现有的LegacyFileProcessor能够以新的接口FileProcessor被使用，而无需修改其内部逻辑
 * 这种设计模式常用于当希望将一个类的接口转换成客户端所期待的另一个接口时
 *
 * @author chance
 * @date 2024/12/5 15:24
 * @since 1.0
 */
public interface FileProcessor {

    /**
     * 处理指定路径的文件
     * 该方法负责读取文件内容，并进行相应的处理操作具体的处理逻辑
     * 可能包括解析文件内容、进行数据转换或者调用其他服务来处理文件数据
     *
     * @param filePath 文件路径，表示需要处理的文件位置不能为空
     */
    void processFile(String filePath);
}
