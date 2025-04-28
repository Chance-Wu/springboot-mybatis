package com.chance.designpatterns.abstractdocument.cms;

/**
 * 视频内容的具体实现
 *
 * @author chance
 * @date 2024/11/29 09:55
 * @since 1.0
 */
public class VideoContent implements Content {

    /**
     * 视频的URL地址
     */
    private final String videoUrl;

    /**
     * 构造一个新的视频内容实例
     *
     * @param videoUrl 视频的URL地址
     */
    public VideoContent(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     * 获取内容的类型
     *
     * @return 返回内容类型，此处固定为"video"
     */
    @Override
    public String getType() {
        return "video";
    }

    /**
     * 渲染内容为HTML格式
     * 对于视频内容，这将生成一个HTML视频标签
     *
     * @return 视频内容的HTML表示
     */
    @Override
    public String render() {
        return "<video controls><source src=\"" + videoUrl + "\" type=\"video/mp4\"></video>";
    }
}
