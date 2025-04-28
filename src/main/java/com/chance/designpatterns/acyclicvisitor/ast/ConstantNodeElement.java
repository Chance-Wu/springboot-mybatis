package com.chance.designpatterns.acyclicvisitor.ast;

/**
 * 常量节点元素
 * <p>表示抽象语法树（AST）中的常量节点。
 * 该类主要用于封装一个常量值，并提供一个方法供访问者访问。
 *
 * @author chance
 * @date 2024/12/5 10:52
 * @since 1.0
 */
public class ConstantNodeElement implements NodeElement {

    /**
     * 常量值
     */
    private int value;

    /**
     * 构造函数，初始化常量节点的值。
     *
     * @param value 要设置的常量值
     */
    public ConstantNodeElement(int value) {
        this.value = value;
    }

    /**
     * 获取常量节点的值。
     *
     * @return 常量节点的值
     */
    public int getValue() {
        return value;
    }

    /**
     * 接受一个访问者对象并调用其访问方法。
     *
     * @param visitor 访问者对象
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
