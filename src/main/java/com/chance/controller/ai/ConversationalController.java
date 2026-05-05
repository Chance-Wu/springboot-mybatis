package com.chance.controller.ai;

import com.chance.service.ai.ConversationalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多轮对话控制器
 * 支持带记忆的连续对话
 *
 * @author chance
 * @date 2026/5/4 16:05
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/conversation")
@Tag(name = "多轮对话", description = "支持记忆的连续对话接口")
public class ConversationalController {

    @Resource
    private ConversationalService conversationalService;

    /**
     * 发送消息进行对话（带记忆）
     *
     * @param userId  用户ID（用于标识对话会话）
     * @param message 用户消息
     * @return AI回复
     */
    @PostMapping("/chat")
    @Operation(summary = "发送消息", description = "发送消息并获取AI回复，支持多轮对话记忆")
    public Map<String, Object> chat(
            @RequestParam String userId,
            @RequestParam String message) {

        log.info("用户 {} 发送消息: {}", userId, message);

        Map<String, Object> result = new HashMap<>();

        try {
            String response = conversationalService.chat(userId, message);

            result.put("success", true);
            result.put("userId", userId);
            result.put("message", message);
            result.put("response", response);
            result.put("activeSessions", conversationalService.getActiveSessionCount());

        } catch (Exception e) {
            log.error("对话处理失败", e);
            result.put("success", false);
            result.put("error", "对话处理失败: " + e.getMessage());
        }

        return result;
    }

    /**
     * 清除用户的对话记忆
     *
     * @param userId 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/memory")
    @Operation(summary = "清除记忆", description = "清除指定用户的对话历史记忆")
    public Map<String, Object> clearMemory(@RequestParam String userId) {
        log.info("清除用户 {} 的对话记忆", userId);

        Map<String, Object> result = new HashMap<>();

        try {
            conversationalService.clearMemory(userId);

            result.put("success", true);
            result.put("userId", userId);
            result.put("message", "对话记忆已清除");
            result.put("activeSessions", conversationalService.getActiveSessionCount());

        } catch (Exception e) {
            log.error("清除记忆失败", e);
            result.put("success", false);
            result.put("error", "清除记忆失败: " + e.getMessage());
        }

        return result;
    }

    /**
     * 获取活跃会话数量
     *
     * @return 会话统计信息
     */
    @GetMapping("/stats")
    @Operation(summary = "获取统计信息", description = "获取当前活跃会话数量等统计信息")
    public Map<String, Object> getStats() {
        Map<String, Object> result = new HashMap<>();
        result.put("activeSessions", conversationalService.getActiveSessionCount());
        result.put("success", true);
        return result;
    }
}
