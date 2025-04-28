package com.chance.designpatterns.acyclicvisitor.permission;

/**
 * 权限访问者接口
 *
 * @author chance
 * @date 2024/12/5 14:04
 * @since 1.0
 */
public interface PermissionVisitor {

    void visit(FileResourceElement fileResourceElement);

    void visit(FolderResourceElement folderResourceElement);
}
