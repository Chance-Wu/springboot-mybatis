package com.chance.designpatterns.acyclicvisitor.ast;

/**
 * 访问者
 * <p>实现了 {@link Visitor} 接口，用于遍历和计算抽象语法树（AST）。
 * 它的主要作用是计算 AST 表达式的数值结果。
 *
 * @author chance
 * @date 2024/12/5 10:56
 * @since 1.0
 */
public class EvaluationVisitor implements Visitor {

    /**
     * 用于存储计算结果的变量
     */
    private int result;

    /**
     * 获取计算结果的方法。
     *
     * @return 计算结果
     */
    public int getResult() {
        return result;
    }

    /**
     * 访问常数节点时的处理逻辑。
     * 将常数节点的值赋给结果变量。
     *
     * @param constantNode 常数节点
     */
    @Override
    public void visit(ConstantNodeElement constantNode) {
        result = constantNode.getValue();
    }

    /**
     * 访问加法节点时的处理逻辑。
     * 分别计算加法节点的左子节点和右子节点的值，并将它们相加。
     *
     * @param addNode 加法节点
     */
    @Override
    public void visit(AddNodeElement addNode) {
        addNode.getLeft().accept(this);
        int leftValue = result;
        addNode.getRight().accept(this);
        int rightValue = result;
        result = leftValue + rightValue;
    }

    /**
     * 访问减法节点时的处理逻辑。
     * 分别计算减法节点的左子节点和右子节点的值，并将它们相减。
     *
     * @param subtractNode 减法节点
     */
    @Override
    public void visit(SubtractNodeElement subtractNode) {
        subtractNode.getLeft().accept(this);
        int leftValue = result;
        subtractNode.getRight().accept(this);
        int rightValue = result;
        result = leftValue - rightValue;
    }
}
