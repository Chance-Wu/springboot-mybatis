package com.chance.designpatterns.acyclicvisitor.ast;

/**
 * 减法节点元素
 * <p>该类实现了 {@link NodeElement} 接口，用于构建减法操作节点。
 * 主要职责是接受访问者对象以执行减法操作，并提供对左子节点和右子节点的访问。
 *
 * @author chance
 * @date 2024/12/5 10:55
 * @since 1.0
 */
public class SubtractNodeElement implements NodeElement {

    /**
     * 左子节点。
     */
    private NodeElement left;

    /**
     * 右子节点。
     */
    private NodeElement right;

    /**
     * 构造一个新的减法节点。
     *
     * @param left  左子节点
     * @param right 右子节点
     */
    public SubtractNodeElement(NodeElement left, NodeElement right) {
        this.left = left;
        this.right = right;
    }

    /**
     * 获取左子节点。
     *
     * @return 左子节点
     */
    public NodeElement getLeft() {
        return left;
    }

    /**
     * 获取右子节点。
     *
     * @return 右子节点
     */
    public NodeElement getRight() {
        return right;
    }

    /**
     * 接受访问者对象。
     * 访问者模式的一部分，允许访问者对象访问当前节点并执行相应操作。
     *
     * @param visitor 访问者对象
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
