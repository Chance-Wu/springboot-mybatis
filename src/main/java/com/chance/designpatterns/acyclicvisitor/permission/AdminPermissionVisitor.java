package com.chance.designpatterns.acyclicvisitor.permission;

/**
 * 管理员访问者实现
 *
 * @author chance
 * @date 2024/12/5 14:05
 * @since 1.0
 */
public class AdminPermissionVisitor implements PermissionVisitor {

    @Override
    public void visit(FileResourceElement fileResourceElement) {
        System.out.println("Admin has full access to file: " + fileResourceElement.getFileName());
    }

    @Override
    public void visit(FolderResourceElement folderResourceElement) {
        System.out.println("Admin has full access to folder: " + folderResourceElement.getFolderName());
    }
}
