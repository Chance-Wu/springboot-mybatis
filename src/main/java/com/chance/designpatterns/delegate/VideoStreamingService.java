package com.chance.designpatterns.delegate;

/**
 * 视频流服务接口
 *
 * @author chance
 * @date 2024/12/30 16:31
 * @since 1.0
 */
public interface VideoStreamingService {

    /**
     * 执行视频流处理
     * 该方法负责处理视频流数据，可以包括但不限于视频数据的接收、处理和转发
     * 具体处理逻辑取决于服务的实现，可能涉及视频格式的转换、视频内容的分析等操作
     */
    void doProcessing();
}
