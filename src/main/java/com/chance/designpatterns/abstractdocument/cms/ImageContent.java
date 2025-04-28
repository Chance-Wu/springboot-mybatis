package com.chance.designpatterns.abstractdocument.cms;

/**
 * 图片内容的具体实现
 * <p>该类实现了{@link Content}接口，用于处理和渲染图片内容
 *
 * @author chance
 * @date 2024/11/29 09:51
 * @since 1.0
 */
public class ImageContent implements Content {

    /**
     * 图片的URL地址
     */
    private final String imageUrl;

    /**
     * 构造函数，用于创建ImageContent对象
     *
     * @param imageUrl 图片的URL地址
     */
    public ImageContent(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 获取内容类型
     * 重写Content接口的getType方法，返回图片类型
     *
     * @return 内容类型，此处固定为"image"
     */
    @Override
    public String getType() {
        return "image";
    }

    /**
     * 渲染内容
     * 重写Content接口的render方法，将图片内容渲染为HTML格式
     *
     * @return 渲染后的HTML字符串，包含img标签和图片URL
     */
    @Override
    public String render() {
        return "<img src=\"" + imageUrl + "\" alt=\"Image\">";
    }
}
