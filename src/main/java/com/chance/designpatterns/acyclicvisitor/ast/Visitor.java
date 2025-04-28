package com.chance.designpatterns.acyclicvisitor.ast;

/**
 * 访问者接口
 * <p>定义了访问者可以访问抽象语法树（AST）中不同类型的节点的方法
 * 它是访问者模式的一部分，允许在不修改节点类的前提下，为AST的元素添加新的操作
 *
 * @author chance
 * @date 2024/12/5 10:50
 * @since 1.0
 */
public interface Visitor {

    /**
     * 访问常量节点
     * 这个方法允许访问者对AST中的常量节点执行操作
     *
     * @param constantNode 常量节点，表示AST中的一个常数值
     */
    void visit(ConstantNodeElement constantNode);

    /**
     * 访问加法节点
     * 这个方法允许访问者对AST中的加法节点执行操作
     *
     * @param addNode 加法节点，表示AST中的一个加法操作
     */
    void visit(AddNodeElement addNode);

    /**
     * 访问减法节点
     * 这个方法允许访问者对AST中的减法节点执行操作
     *
     * @param subtractNode 减法节点，表示AST中的一个减法操作
     */
    void visit(SubtractNodeElement subtractNode);
}
