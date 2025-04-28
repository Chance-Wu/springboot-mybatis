package com.chance.designpatterns.abstractdocument.excel;

/**
 * 公式单元格
 * <p>实现了{@link Cell}接口，用于表示Excel表格中的公式类型单元格
 * 公式单元格包含一个公式字符串和一个计算结果
 *
 * @author chance
 * @date 2024/11/29 14:27
 * @since 1.0
 */
public class FormulaCell implements Cell {

    /**
     * 公式字符串，用于表示单元格中的计算公式
     */
    private final String formula;

    /**
     * 计算结果，公式计算后的数值结果
     */
    private final double result;


    /**
     * 构造函数，用于创建一个公式单元格对象
     *
     * @param formula 公式字符串
     * @param result  计算结果
     */
    public FormulaCell(String formula, double result) {
        this.formula = formula;
        this.result = result;
    }

    /**
     * 获取单元格的值
     * 对于公式单元格，返回的是计算结果
     *
     * @return 单元格的值（计算结果）
     */
    @Override
    public Object getValue() {
        return result;
    }

    /**
     * 显示单元格的内容
     * 对于公式单元格，以"=公式 (计算结果)"的格式显示
     *
     * @return 单元格的显示内容
     */
    @Override
    public String display() {
        return "=" + formula + " (" + result + ")";
    }
}
