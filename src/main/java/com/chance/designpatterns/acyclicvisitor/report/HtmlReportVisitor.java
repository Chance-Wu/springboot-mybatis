package com.chance.designpatterns.acyclicvisitor.report;

/**
 * HtmlReportVisitor 类实现了一个具体的报告访问者，用于生成 HTML 格式的报告。
 * 它通过访问不同的数据类型（如销售数据和开支数据）来收集信息，并生成相应的 HTML 报告。
 *
 * @author chance
 * @date 2024/12/5 13:45
 * @since 1.0
 */
public class HtmlReportVisitor implements ReportVisitor {

    /**
     * 用于存储生成的 HTML 报告内容
     */
    private StringBuilder report = new StringBuilder();

    /**
     * 访问 SalesData 类型的对象，生成销售数据的 HTML 报告。
     *
     * @param sales SalesData 类型的对象，包含销售数据
     */
    @Override
    public void visit(SalesData sales) {
        report.append("<h1>Sales Data</h1>")
                .append("<p>Total Sales: $").append(sales.getTotalSales()).append("</p>");
    }

    /**
     * 访问 ExpenseData 类型的对象，生成开支数据的 HTML 报告。
     *
     * @param expense ExpenseData 类型的对象，包含开支数据
     */
    @Override
    public void visit(ExpenseData expense) {
        report.append("<h1>Expense Data</h1>")
                .append("<p>Total Expenses: $").append(expense.getTotalExpenses()).append("</p>");
    }

    /**
     * 获取生成的 HTML 报告内容。
     *
     * @return 以字符串形式返回完整的 HTML 报告
     */
    public String getReport() {
        return report.toString();
    }
}
