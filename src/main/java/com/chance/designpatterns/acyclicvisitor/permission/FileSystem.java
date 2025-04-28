package com.chance.designpatterns.acyclicvisitor.permission;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源结构
 *
 * @author chance
 * @date 2024/12/5 14:16
 * @since 1.0
 */
public class FileSystem {

    private List<ResourceElement> resourceElements = new ArrayList<>();

    public void addResourceElement(ResourceElement resourceElement) {
        resourceElements.add(resourceElement);
    }

    public void checkPermissions(PermissionVisitor permissionVisitor) {
        for (ResourceElement resourceElement : resourceElements) {
            resourceElement.accept(permissionVisitor);
        }
    }
}
