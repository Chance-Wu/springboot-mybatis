package com.chance.designpatterns.abstractdocument.excel;

/**
 * 数字单元格
 * <p>实现了{@link Cell}接口，用于表示Excel表格中的数字类型单元格
 *
 * @author chance
 * @date 2024/11/29 14:17
 * @since 1.0
 */
public class NumberCell implements Cell {

    private final double value;

    public NumberCell(double value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String display() {
        return String.valueOf(value);
    }
}
