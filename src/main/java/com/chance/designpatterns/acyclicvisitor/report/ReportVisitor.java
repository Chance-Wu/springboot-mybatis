package com.chance.designpatterns.acyclicvisitor.report;

/**
 * 访问者接口
 *
 * @author chance
 * @date 2024/12/5 13:41
 * @since 1.0
 */
public interface ReportVisitor {

    void visit(SalesData salesData);

    void visit(ExpenseData expenseData);
}
