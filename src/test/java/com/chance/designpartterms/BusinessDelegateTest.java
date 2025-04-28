package com.chance.designpartterms;

import com.chance.designpatterns.delegate.BusinessDelegate;
import com.chance.designpatterns.delegate.BusinessLookup;
import com.chance.designpatterns.delegate.MobileClient;
import com.chance.designpatterns.delegate.impl.NetflixService;
import com.chance.designpatterns.delegate.impl.YouTubeService;
import org.junit.Test;

/**
 * @author chance
 * @date 2024/12/31 09:46
 * @since 1.0
 */
public class BusinessDelegateTest {

    @Test
    public void test() {
        // 准备对象实例
        // 初始化业务代理和业务查找对象，为后续设置服务做准备
        BusinessDelegate businessDelegate = new BusinessDelegate();
        BusinessLookup businessLookup = new BusinessLookup();

        // 设置具体的业务服务实现
        // 分别设置NetflixService和YouTubeService作为具体的业务服务实现
        businessLookup.setNetflixService(new NetflixService());
        businessLookup.setYouTubeService(new YouTubeService());

        // 将业务查找对象设置到业务代理中
        // 使得业务代理可以通过业务查找对象找到对应的服务实现
        businessDelegate.setBusinessLookup(businessLookup);

        // 创建客户端并使用业务代理
        // 创建一个移动客户端，并将业务代理传递给它，以便客户端可以通过业务代理访问业务服务
        MobileClient client = new MobileClient(businessDelegate);

        // 使用客户端播放电影
        // 播放两部电影，以演示客户端通过业务代理调用不同服务的能力
        client.playbackMovie("Die Hard 2");
        client.playbackMovie("Maradona: The Greatest Ever");
    }
}
