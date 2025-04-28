package com.chance.designpatterns.abstractdocument.cms;

import java.util.List;

/**
 * 文章，用来存储和渲染多种内容
 *
 * @author chance
 * @date 2024/11/29 09:57
 * @since 1.0
 */
public class Article {

    private List<Content> contents;

    public Article(List<Content> contents) {
        this.contents = contents;
    }

    public String render() {
        StringBuilder sb = new StringBuilder();
        for (Content content : contents) {
            sb.append(content.render());
        }
        return sb.toString();
    }
}
