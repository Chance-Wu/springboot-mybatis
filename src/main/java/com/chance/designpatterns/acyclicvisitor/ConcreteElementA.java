package com.chance.designpatterns.acyclicvisitor;

/**
 * 具体元素A
 * <p>该类实现了{@link Element}接口，为具体元素A定义了接受访问者访问的方法
 * 主要用途是允许访问者以特定方式访问元素，而无需修改元素类本身
 *
 * @author chance
 * @date 2024/12/5 10:12
 * @since 1.0
 */
public class ConcreteElementA implements Element {

    /**
     * 接受访问者访问
     * 该方法是Visitor模式的核心，它允许访问者访问元素并执行相应操作
     * 通过调用visitor的visit方法，将自身作为参数传递，以允许访问者访问当前元素
     *
     * @param visitor 访问者对象，用于执行对当前元素的访问
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    /**
     * 执行操作A
     * 该方法定义了具体元素A的一个业务操作，输出一条消息表示执行了操作A
     * 这个操作是ConcreteElementA特有的，展示了元素的具体行为
     */
    public void operationA() {
        System.out.println("Operation A in ConcreteElementA");
    }

}
