package com.chance.designpatterns.acyclicvisitor.report;

/**
 * @author chance
 * @date 2024/12/5 13:43
 * @since 1.0
 */
public class ExpenseData implements ReportElement {

    private double totalExpenses;

    public ExpenseData(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    @Override
    public void accept(ReportVisitor visitor) {
        visitor.visit(this);
    }
}
