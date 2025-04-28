package com.chance.designpatterns.acyclicvisitor.permission;

/**
 * 用户权限访问者实现
 *
 * @author chance
 * @date 2024/12/5 14:06
 * @since 1.0
 */
public class UserPermissionVisitor implements PermissionVisitor {

    @Override
    public void visit(FileResourceElement fileResourceElement) {
        System.out.println("User has read-only access to file: " + fileResourceElement.getFileName());
    }

    @Override
    public void visit(FolderResourceElement folderResourceElement) {
        System.out.println("User has no access to folder: " + folderResourceElement.getFolderName());
    }
}
