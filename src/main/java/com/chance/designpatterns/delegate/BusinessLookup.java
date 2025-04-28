package com.chance.designpatterns.delegate;

import com.chance.designpatterns.delegate.impl.NetflixService;
import com.chance.designpatterns.delegate.impl.YouTubeService;

/**
 * 根据电影名称选择合适的视频流服务。
 *
 * @author chance
 * @date 2024/12/30 16:46
 * @since 1.0
 */
public class BusinessLookup {

    /**
     * Netflix 服务实例，用于访问 Netflix 的功能
     */
    private NetflixService netflixService;

    /**
     * YouTube 服务实例，用于访问 YouTube 的功能
     */
    private YouTubeService youTubeService;

    public void setNetflixService(NetflixService netflixService) {
        this.netflixService = netflixService;
    }

    public void setYouTubeService(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    /**
     * 根据电影名称选择视频流服务。
     *
     * @param movie 电影名称
     * @return 返回选定的视频流服务实例
     */
    public VideoStreamingService getBusinessService(String movie) {
        // 根据电影名称是否包含 "die hard" 来选择视频流服务
        if (movie.toLowerCase().contains("die hard")) {
            return netflixService;
        } else {
            return youTubeService;
        }
    }
}
