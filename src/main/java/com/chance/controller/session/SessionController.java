//package com.chance.controller.session;
//
//import com.github.xiaoymin.knife4j.annotations.ApiSupport;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//
/// **
// * @author chance
// * @date 2025/7/15 15:22
// * @since 1.0
// */
//@ApiSupport(author = "chance")
//@Slf4j
//@Tag(name = "session管理类", description = "用于简单测试spring session")
//@RestController
//@RequestMapping
//public class SessionController {
//
//    @Operation(summary = "测试存入 session", description = "测试存入 session")
//    @RequestMapping("/putIntoSession")
//    public String putIntoSession(HttpServletRequest request) {
//        request.getSession().setAttribute("name", "leo");
//        return "ok";
//    }
//
//    @Operation(summary = "测试获取 session内容", description = "测试获取 session内容")
//    @RequestMapping("/getFromSession")
//    public String getFromSession(HttpServletRequest request) {
//        return String.valueOf(request.getSession().getAttribute("name"));
//    }
//}
