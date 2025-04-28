package com.chance.designpatterns.acyclicvisitor.ast;

/**
 * 节点元素接口
 * <p>定义接受访问者接口的方法，允许访问者访问并执行操作
 * 此设计模式允许我们在不修改现有类结构的情况下，为元素添加新的操作
 *
 * @author chance
 * @date 2024/12/5 10:49
 * @since 1.0
 */
public interface NodeElement {

    /**
     * 接受访问者访问
     * 元素通过此方法允许访问者对其进行操作
     *
     * @param visitor 访问者接口，元素将调用访问者的方法来执行操作
     */
    void accept(Visitor visitor);
}
