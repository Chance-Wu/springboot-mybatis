package com.chance.designpatterns.acyclicvisitor.ast;

/**
 * 加法节点元素
 * 它实现了 {@link NodeElement} 接口，并包含两个子节点，用于表示加法操作的左侧和右侧操作数。
 * 该类的主要作用是通过 accept 方法接受访问者的访问，并将访问者引导至正确的访问方法。
 *
 * @author chance
 * @date 2024/12/5 10:53
 * @since 1.0
 */
public class AddNodeElement implements NodeElement {

    /**
     * 左侧子节点，代表加法操作的左侧操作数
     */
    private NodeElement left;

    /**
     * 右侧子节点，代表加法操作的右侧操作数
     */
    private NodeElement right;

    /**
     * 构造方法，初始化加法节点的左右子节点。
     *
     * @param left  左侧子节点
     * @param right 右侧子节点
     */
    public AddNodeElement(NodeElement left, NodeElement right) {
        this.left = left;
        this.right = right;
    }

    /**
     * 获取左侧子节点。
     *
     * @return 左侧子节点
     */
    public NodeElement getLeft() {
        return left;
    }

    /**
     * 获取右侧子节点。
     *
     * @return 右侧子节点
     */
    public NodeElement getRight() {
        return right;
    }

    /**
     * 接受访问者访问。
     * 该方法是 NodeElement 接口中定义的方法，用于允许访问者访问当前节点。
     * 访问者将根据当前节点的类型决定如何进行访问。
     *
     * @param visitor 访问者对象，用于访问当前节点
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
