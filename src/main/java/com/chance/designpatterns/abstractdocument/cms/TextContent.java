package com.chance.designpatterns.abstractdocument.cms;

/**
 * 文本内容的具体实现
 * <p>该类实现了{@link Content}接口，用于定义和管理文本类型的内容
 * 主要功能包括返回内容的类型标识和渲染内容的HTML格式
 *
 * @author chance
 * @date 2024/11/29 09:50
 * @since 1.0
 */
public class TextContent implements Content {

    /**
     * 存储文本内容的字符串变量
     */
    private final String text;

    /**
     * 构造函数，用于创建TextContent实例
     *
     * @param text 文本内容
     */
    public TextContent(String text) {
        this.text = text;
    }

    /**
     * 获取内容的类型
     *
     * @return 返回内容类型，此处固定为"text"
     */
    @Override
    public String getType() {
        return "text";
    }

    /**
     * 渲染文本内容为HTML格式
     * <p>
     * 此方法将文本内容封装在HTML的 <p> 标签中，以便在Web页面上显示
     *
     * @return 返回封装好的HTML格式文本内容
     */
    @Override
    public String render() {
        return "<p>" + text + "</p>";
    }
}
