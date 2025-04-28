package com.chance.designpatterns.acyclicvisitor.permission;

/**
 * 文件夹资源元素
 *
 * @author chance
 * @date 2024/12/5 14:03
 * @since 1.0
 */
public class FolderResourceElement implements ResourceElement {

    private String folderName;

    public FolderResourceElement(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }

    @Override
    public void accept(PermissionVisitor permissionVisitor) {
        permissionVisitor.visit(this);
    }
}
