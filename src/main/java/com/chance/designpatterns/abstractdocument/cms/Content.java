package com.chance.designpatterns.abstractdocument.cms;

/**
 * 通用的 Content 接口
 * <p>定义了内容对象的基本操作，旨在为不同类型的内容提供一个统一的处理方式
 * 主要用途是获取内容的类型信息，并渲染为字符串形式
 *
 * @author chance
 * @date 2024/11/29 09:49
 * @since 1.0
 */
public interface Content {

    /**
     * 获取内容的类型
     *
     * @return 内容的类型，如文本、图片、视频等
     */
    String getType();

    /**
     * 渲染内容
     * 将内容渲染为字符串形式，具体表现取决于内容的类型和实现
     *
     * @return 渲染后的内容字符串
     */
    String render();
}
