package com.chance.designpatterns.acyclicvisitor;

/**
 * 实现具体访问者
 * <p>该类实现了{@link Visitor}接口，定义了访问者的行为
 * 它包含两个方法，分别用于访问和操作两种不同的元素类型
 *
 * @author chance
 * @date 2024/12/5 10:14
 * @since 1.0
 */
public class ConcreteVisitor implements Visitor {

    /**
     * 访问和操作ConcreteElementA类型元素的方法
     * 该方法被调用时，会执行elementA的operationA方法
     * 并打印访问了ConcreteElementA的信息
     *
     * @param elementA 被访问的ConcreteElementA类型元素
     */
    @Override
    public void visit(ConcreteElementA elementA) {
        elementA.operationA();
        System.out.println("Visited ConcreteElementA");
    }

    /**
     * 访问和操作ConcreteElementB类型元素的方法
     * 该方法被调用时，会执行elementB的operationB方法
     * 并打印访问了ConcreteElementB的信息
     *
     * @param elementB 被访问的ConcreteElementB类型元素
     */
    @Override
    public void visit(ConcreteElementB elementB) {
        elementB.operationB();
        System.out.println("Visited ConcreteElementB");
    }
}
