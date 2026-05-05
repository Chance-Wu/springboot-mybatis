package com.chance.controller.ai;

import com.chance.service.ai.RagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chance
 * @date 2026/5/4 14:48
 * @since 1.0
 */
@RestController
@RequestMapping("/rag")
public class RagController {

    @Resource
    private RagService ragService;

    @GetMapping("/ask")
    public String ask(String question) {
        return ragService.ask(question);
    }
}
