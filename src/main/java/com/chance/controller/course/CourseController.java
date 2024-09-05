package com.chance.controller.course;


import com.chance.common.CommonRsp;
import com.chance.entity.vo.CourseRequest;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chance
 * @since 2020-08-22
 */
@ApiSupport(author = "chance")
@Tag(name = "课程管理")
@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    @Operation(summary = "添加课程")
    @PostMapping("/validate")
    public CommonRsp<String> addCourse(@RequestBody @Validated CourseRequest courseRequest) {
        log.info(courseRequest.getCName());
        return CommonRsp.success("success");
    }
}
