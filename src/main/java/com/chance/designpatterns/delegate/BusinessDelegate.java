package com.chance.designpatterns.delegate;

/**
 * 业务代理类，用于封装对业务服务的请求
 * 它通过一个业务查找对象来定位并激活相应的业务服务
 *
 * @author chance
 * @date 2024/12/30 16:51
 * @since 1.0
 */
public class BusinessDelegate {

    /**
     * 业务查找对象，用于获取具体的业务服务
     */
    private BusinessLookup businessLookup;

    public void setBusinessLookup(BusinessLookup businessLookup) {
        this.businessLookup = businessLookup;
    }

    /**
     * 播放电影的方法
     * 根据电影名称查找并激活相应的视频流服务进行电影播放
     *
     * @param movie 电影名称，用以确定使用哪个视频流服务
     */
    public void playbackMovie(String movie) {
        // 根据电影名称获取对应的视频流服务实例
        VideoStreamingService videoStreamingService = businessLookup.getBusinessService(movie);
        // 调用视频流服务的处理方法，开始播放电影
        videoStreamingService.doProcessing();
    }
}
