package com.chance.designpatterns.acyclicvisitor.permission;

/**
 * 资源元素接口
 *
 * @author chance
 * @date 2024/12/5 14:00
 * @since 1.0
 */
public interface ResourceElement {

    void accept(PermissionVisitor permissionVisitor);
}
