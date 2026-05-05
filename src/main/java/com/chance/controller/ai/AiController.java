package com.chance.controller.ai;

import com.chance.component.ai.AiGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chance
 * @date 2026/5/4 14:20
 * @since 1.0
 */
@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private AiGateway aiGateway;

    @GetMapping("/chat")
    public String chat(String msg) {
        return aiGateway.chat(msg);
    }

    @GetMapping("/sql/optimize")
    public String optimize(String sql) {
        return aiGateway.optimizeSql(sql);
    }

    @GetMapping("/code/gen")
    public String gen(String req) {
        return aiGateway.generateCode(req);
    }

}
