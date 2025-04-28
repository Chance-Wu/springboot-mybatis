package com.chance.designpatterns.acyclicvisitor;

/**
 * 元素接口，定义接受访问者访问的接口方法
 * <p>主要用于访问者模式中，使得不同的访问者可以对一组元素进行不同的操作，而不需要修改元素类本身
 *
 * @author chance
 * @date 2024/12/5 10:11
 * @since 1.0
 */
public interface Element {


    /**
     * 接受访问者访问的方法
     * 允许访问者通过此方法访问元素，并执行访问者定义的操作
     *
     * @param visitor 访问者对象，实现了访问者接口，定义了对元素进行操作的方法
     */
    void accept(Visitor visitor);
}
