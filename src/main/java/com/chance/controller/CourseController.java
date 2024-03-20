package com.chance.controller;


import com.chance.entity.vo.CourseRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chance
 * @since 2020-08-22
 */
@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    @PostMapping("/validate")
    public String addCourse(@Validated CourseRequest courseRequest) {
        log.info(courseRequest.getCName());
        return "success";
    }
}
