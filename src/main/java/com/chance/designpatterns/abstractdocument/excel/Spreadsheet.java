package com.chance.designpatterns.abstractdocument.excel;

/**
 * 电子表格
 * <p>用于管理单元格对象
 * 它提供了设置单元格内容和获取单元格显示内容的方法
 *
 * @author chance
 * @date 2024/11/29 14:41
 * @since 1.0
 */
public class Spreadsheet {

    /**
     * 二维数组存储单元格对象
     */
    private Cell[][] cells;

    /**
     * 构造方法，初始化电子表格的行数和列数
     *
     * @param rows 行数
     * @param cols 列数
     */
    public Spreadsheet(int rows, int cols) {
        cells = new Cell[rows][cols];
    }

    /**
     * 设置指定位置的单元格内容
     *
     * @param row  行号
     * @param col  列号
     * @param cell 单元格对象
     */
    public void setCell(int row, int col, Cell cell) {
        cells[row][col] = cell;
    }

    /**
     * 获取指定位置的单元格显示内容
     * 如果该位置没有单元格对象，则返回空字符串
     *
     * @param row 行号
     * @param col 列号
     * @return 单元格的显示内容或空字符串
     */
    public String getDisplay(int row, int col) {
        if (cells[row][col] != null) {
            return cells[row][col].display();
        } else {
            return "";
        }
    }

}
