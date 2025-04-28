package com.chance.designpatterns.acyclicvisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象解构
 * <p>该类用于管理一个元素集合，并提供访问者访问这些元素的方法
 * 主要职责包括：
 * 1. 添加元素到集合中
 * 2. 接受访问者的访问
 *
 * @author chance
 * @date 2024/12/5 10:15
 * @since 1.0
 */
public class ObjectStructure {

    /**
     * 元素集合，用于存储待访问的元素
     */
    private List<Element> elements = new ArrayList<>();

    /**
     * 添加元素到对象解构中
     *
     * @param element 要添加的元素
     */
    public void addElement(Element element) {
        elements.add(element);
    }

    /**
     * 接受访问者的访问
     * 此方法遍历元素集合，调用每个元素的accept方法以接受访问
     *
     * @param visitor 执行访问的访问者对象
     */
    public void accept(Visitor visitor) {
        for (Element element : elements) {
            element.accept(visitor);
        }
    }
}
