package com.chance.designpatterns.acyclicvisitor;

/**
 * 访问者接口，定义了访问元素（Element）的接口
 * <p>允许在不修改元素类的前提下，定义作用于元素的新操作。
 * 该设计模式主要用于遍历元素集合，并以适当的操作应用于集合中的每个元素。
 *
 * @author chance
 * @date 2024/12/5 10:11
 * @since 1.0
 */
public interface Visitor {

    /**
     * 访问并作用于ConcreteElementA类型的元素。
     * 该方法使得Visitor能够以特定的方式处理或访问ConcreteElementA元素。
     *
     * @param elementA 被访问的ConcreteElementA类型的元素。
     */
    void visit(ConcreteElementA elementA);

    /**
     * 访问并作用于ConcreteElementB类型的元素。
     * 该方法使得Visitor能够以特定的方式处理或访问ConcreteElementB元素。
     *
     * @param elementB 被访问的ConcreteElementB类型的元素。
     */
    void visit(ConcreteElementB elementB);
}
