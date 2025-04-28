package com.chance.designpartterms;

import com.chance.designpatterns.acyclicvisitor.ConcreteElementA;
import com.chance.designpatterns.acyclicvisitor.ConcreteElementB;
import com.chance.designpatterns.acyclicvisitor.ConcreteVisitor;
import com.chance.designpatterns.acyclicvisitor.ObjectStructure;
import com.chance.designpatterns.acyclicvisitor.ast.AddNodeElement;
import com.chance.designpatterns.acyclicvisitor.ast.ConstantNodeElement;
import com.chance.designpatterns.acyclicvisitor.ast.EvaluationVisitor;
import com.chance.designpatterns.acyclicvisitor.ast.NodeElement;
import com.chance.designpatterns.acyclicvisitor.ast.SubtractNodeElement;
import com.chance.designpatterns.acyclicvisitor.permission.AdminPermissionVisitor;
import com.chance.designpatterns.acyclicvisitor.permission.FileResourceElement;
import com.chance.designpatterns.acyclicvisitor.permission.FileSystem;
import com.chance.designpatterns.acyclicvisitor.permission.PermissionVisitor;
import com.chance.designpatterns.acyclicvisitor.permission.UserPermissionVisitor;
import com.chance.designpatterns.acyclicvisitor.report.ExpenseData;
import com.chance.designpatterns.acyclicvisitor.report.HtmlReportVisitor;
import com.chance.designpatterns.acyclicvisitor.report.ReportElement;
import com.chance.designpatterns.acyclicvisitor.report.SalesData;
import org.junit.Test;

/**
 * @author chance
 * @date 2024/12/5 10:27
 * @since 1.0
 */
public class AcyclicVisitorTest {

    @Test
    public void permission() {
        // 创建文件系统（数据结构）
        FileSystem fileSystem = new FileSystem();
        fileSystem.addResourceElement(new FileResourceElement("document.txt"));
        fileSystem.addResourceElement(new FileResourceElement("photos"));

        // 创建管理员和普通用户的访问者（操作）
        PermissionVisitor adminVisitor = new AdminPermissionVisitor();
        PermissionVisitor userVisitor = new UserPermissionVisitor();

        System.out.println("Admin Permissions:");
        fileSystem.checkPermissions(adminVisitor);

        System.out.println("\nUser Permissions:");
        fileSystem.checkPermissions(userVisitor);
    }

    @Test
    public void report() {
        ReportElement salesData = new SalesData(50000);
        ReportElement expenseData = new ExpenseData(20000);

        HtmlReportVisitor htmlReportVisitor = new HtmlReportVisitor();
        salesData.accept(htmlReportVisitor);
        expenseData.accept(htmlReportVisitor);

        System.out.println(htmlReportVisitor.getReport());
    }

    @Test
    public void ast() {
        NodeElement expression = new AddNodeElement(
                new ConstantNodeElement(5),
                new SubtractNodeElement(new ConstantNodeElement(10), new ConstantNodeElement(3))
        );

        EvaluationVisitor evaluator = new EvaluationVisitor();
        expression.accept(evaluator);
        System.out.println("Expression result：" + evaluator.getResult());
    }

    @Test
    public void test() {
        ConcreteElementA elementA = new ConcreteElementA();
        ConcreteElementB elementB = new ConcreteElementB();

        ObjectStructure objectStructure = new ObjectStructure();
        objectStructure.addElement(elementA);
        objectStructure.addElement(elementB);

        ConcreteVisitor visitor = new ConcreteVisitor();
        objectStructure.accept(visitor);

    }
}
