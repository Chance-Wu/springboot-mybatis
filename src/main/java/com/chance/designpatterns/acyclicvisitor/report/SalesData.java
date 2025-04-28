package com.chance.designpatterns.acyclicvisitor.report;

/**
 * 数据类型元素实现
 *
 * @author chance
 * @date 2024/12/5 13:40
 * @since 1.0
 */
public class SalesData implements ReportElement {

    private double totalSales;

    public SalesData(double totalSales) {
        this.totalSales = totalSales;
    }

    public double getTotalSales() {
        return totalSales;
    }

    @Override
    public void accept(ReportVisitor visitor) {
        visitor.visit(this);
    }
}
