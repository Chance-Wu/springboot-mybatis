package com.chance.designpatterns.acyclicvisitor.permission;

/**
 * 文件资源元素
 *
 * @author chance
 * @date 2024/12/5 14:02
 * @since 1.0
 */
public class FileResourceElement implements ResourceElement {

    private String fileName;

    public FileResourceElement(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public void accept(PermissionVisitor permissionVisitor) {
        permissionVisitor.visit(this);
    }
}
