package com.chance.designpatterns.acyclicvisitor.report;

/**
 * 报表元素
 *
 * @author chance
 * @date 2024/12/5 13:37
 * @since 1.0
 */
public interface ReportElement {

    void accept(ReportVisitor visitor);
}
