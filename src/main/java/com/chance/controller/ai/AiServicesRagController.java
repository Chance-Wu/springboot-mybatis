package com.chance.controller.ai;

import com.chance.service.ai.AiServicesRagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 基于AiServices的RAG控制器
 * 提供简洁的REST API接口
 *
 * @author chance
 * @date 2026/5/4 15:35
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/ai-rag")
@Tag(name = "AI RAG服务（AiServices版）", description = "基于AiServices封装的RAG问答接口")
public class AiServicesRagController {

    @Resource
    private AiServicesRagService aiServicesRagService;

    /**
     * 简化的问答接口
     *
     * @param question 用户问题
     * @return AI生成的回答
     */
    @GetMapping("/ask")
    @Operation(summary = "知识库问答", description = "基于企业知识库回答问题")
    public String ask(@RequestParam String question) {
        log.info("收到问答请求: {}", question);
        return aiServicesRagService.ask(question);
    }

    /**
     * 直接提问接口（不经过检索）
     *
     * @param question 用户问题
     * @return AI生成的回答
     */
    @GetMapping("/ask-direct")
    @Operation(summary = "直接提问", description = "直接向AI提问，不检索知识库")
    public String askDirect(@RequestParam String question) {
        log.info("收到直接提问请求: {}", question);
        return aiServicesRagService.askDirect(question);
    }
}
