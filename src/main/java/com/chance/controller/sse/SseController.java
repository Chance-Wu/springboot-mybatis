package com.chance.controller.sse;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chance
 * @date 2025/2/11 13:27
 * @since 1.0
 */
@ApiSupport(author = "chance")
@Slf4j
@Tag(name = "Server-Sent Events (SSE)", description = "用于sse测试")
@RestController
public class SseController {
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @GetMapping(path = "/stream-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSse() {
        SseEmitter emitter = new SseEmitter();

        executorService.execute(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .name("message")
                            .data("Time is " + java.time.LocalTime.now());
                    emitter.send(event);
                    Thread.sleep(5000); // 每5秒发送一次消息
                }
                emitter.complete();
            } catch (IOException e) {
                emitter.completeWithError(e);
            } catch (InterruptedException e) {
                // 恢复中断状态
                Thread.currentThread().interrupt();
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
