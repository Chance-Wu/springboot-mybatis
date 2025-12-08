package com.chance.designpatterns.factory.abstrct;

/**
 * 具体工厂 A：创建 Summer 产品族
 *
 * @author chance
 * @date 2025/12/6 14:40
 * @since 1.0
 */
public class SummerSkinFactory implements SkinFactory {
    @Override
    public Button createButton() {
        return new SummerButton();
    }

    @Override
    public CheckBox createCheckBox() {
        return new SummerCheckBox();
    }
}
