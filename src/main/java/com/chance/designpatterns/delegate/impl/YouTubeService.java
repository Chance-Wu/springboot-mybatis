package com.chance.designpatterns.delegate.impl;

import com.chance.designpatterns.delegate.VideoStreamingService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chance
 * @date 2024/12/30 16:44
 * @since 1.0
 */
@Slf4j
public class YouTubeService implements VideoStreamingService {

    @Override
    public void doProcessing() {
        log.info("YouTubeService is now processing");
    }
}
