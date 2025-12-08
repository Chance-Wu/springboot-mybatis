package com.chance.designpatterns.factory.abstrct;

/**
 * 具体工厂 B：创建 Winter 产品族
 *
 * @author chance
 * @date 2025/12/6 14:42
 * @since 1.0
 */
public class WinterSkinFactory implements SkinFactory {
    @Override
    public Button createButton() {
        return new WinterButton();
    }

    @Override
    public CheckBox createCheckBox() {
        return new WinterCheckBox();
    }
}
