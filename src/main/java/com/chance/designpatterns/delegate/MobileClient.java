package com.chance.designpatterns.delegate;

/**
 * 移动客户端类，用于在移动环境中发起电影播放请求。
 * 该类通过委托给 BusinessDelegate 对象来执行实际的业务逻辑。
 *
 * @author chance
 * @date 2024/12/31 08:56
 * @since 1.0
 */
public class MobileClient {

    /**
     * 业务委托对象，用于处理具体的业务逻辑操作。
     */
    private final BusinessDelegate businessDelegate;

    /**
     * 构造函数，初始化 MobileClient 实例。
     *
     * @param businessDelegate 业务委托对象，用于处理具体的业务逻辑操作。
     */
    public MobileClient(BusinessDelegate businessDelegate) {
        this.businessDelegate = businessDelegate;
    }

    /**
     * 发起电影播放请求。
     * 该方法将播放电影的任务委托给业务委托对象，使得客户端可以在不了解具体实现细节的情况下发起电影播放。
     *
     * @param movie 要播放的电影名称。
     */
    public void playbackMovie(String movie) {
        businessDelegate.playbackMovie(movie);
    }
}
