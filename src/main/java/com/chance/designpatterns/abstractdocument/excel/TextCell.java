package com.chance.designpatterns.abstractdocument.excel;

/**
 * 文本单元格
 * <p>实现了{@link Cell}接口用于表示包含文本数据的单元格对象
 *
 * @author chance
 * @date 2024/11/29 14:20
 * @since 1.0
 */
public class TextCell implements Cell {

    private final String text;

    public TextCell(String text) {
        this.text = text;
    }

    @Override
    public Object getValue() {
        return text;
    }

    @Override
    public String display() {
        return text;
    }
}
