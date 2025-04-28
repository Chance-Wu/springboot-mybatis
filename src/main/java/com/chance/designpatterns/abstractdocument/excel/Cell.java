package com.chance.designpatterns.abstractdocument.excel;

/**
 * 单元格接口
 * <p>定义了电子表格中单元格的基本操作和属性
 * 它提供了获取单元格值和显示单元格内容的方法
 *
 * @author chance
 * @date 2024/11/29 14:11
 * @since 1.0
 */
public interface Cell {

    /**
     * 获取单元格的值
     *
     * @return 单元格的值，类型为Object，以便可以存储各种类型的值
     */
    Object getValue();

    /**
     * 显示单元格的内容
     *
     * @return 以字符串形式返回单元格的内容，便于显示或进一步处理
     */
    String display();
}
