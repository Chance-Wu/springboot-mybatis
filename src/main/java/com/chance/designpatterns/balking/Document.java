package com.chance.designpatterns.balking;

/**
 * @author chance
 * @date 2024/12/13 10:22
 * @since 1.0
 */
public class Document {

    private boolean isModified;

    public void change() {
        isModified = true;
    }

    public void save() {
        if (!isModified) {
            // 如果文档未修改，则不保存
            return;
        }
        // 保存文档
        System.out.println("Document saved.");
        isModified = false;
    }
}
