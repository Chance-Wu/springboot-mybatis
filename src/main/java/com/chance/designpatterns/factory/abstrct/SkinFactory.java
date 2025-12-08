package com.chance.designpatterns.factory.abstrct;

/**
 * 抽象工厂：皮肤工厂，定义了创建产品族的方法
 *
 * @author chance
 * @date 2025/12/6 14:38
 * @since 1.0
 */
public interface SkinFactory {

    Button createButton();

    CheckBox createCheckBox();
}
