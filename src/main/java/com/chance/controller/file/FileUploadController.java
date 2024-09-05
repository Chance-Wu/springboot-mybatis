package com.chance.controller.file;

import com.chance.common.CommonRsp;
import com.chance.entity.dto.PicUploadResult;
import com.chance.service.FileUploadService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> FileUploadController </p>
 *
 * @author chance
 * @date 2023/5/27 15:30
 * @since 1.0
 */
@Tag(name = "文件上传")
@ApiSupport(author = "chance")
@Slf4j
@RestController
@RequestMapping("/file")
public class FileUploadController {

    // 允许上传的格式 图片形式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".png"};

    @Resource
    private FileUploadService fileUploadService;

    @PostMapping("/uploadImg")
    @Operation(summary = "上传图片")
    @Parameters(value = {@Parameter(name = "file", description = "待上传的图片文件", in = ParameterIn.QUERY)})
    public CommonRsp<Map<String, String>> uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        boolean isFlag = false;
        for (String type : IMAGE_TYPE) {
            log.info(file.getOriginalFilename());
            if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
                isFlag = true;
                break;
            }
        }

        if (isFlag) {
            PicUploadResult picUploadResult = fileUploadService.uplodImg(file, request);
            boolean isLegal = picUploadResult.isLegal();

            if (isLegal) {
                Map<String, String> resMap = new HashMap<>();
                resMap.put("imgPath", picUploadResult.getImgPath());
                return CommonRsp.success(resMap);
            } else {
                return CommonRsp.error("图片上传有误");
            }
        } else {
            return CommonRsp.error("上传的图片格式必须为:bmp,jpg,jpeg,png");
        }

    }

    @PostMapping("/uploadManyImg")
    @Operation(summary = "批量上传图片")
    @Parameters(value = {@Parameter(name = "files", description = "待上传的图片文件", in = ParameterIn.QUERY)})
    public CommonRsp<Map<String, List<String>>> uploadManyImg(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
        boolean isFlag = false;
        for (MultipartFile uploadFile : files) {
            for (String type : IMAGE_TYPE) {
                if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
                    isFlag = true;
                    break;
                }
            }
        }

        if (isFlag) {
            PicUploadResult picUploadResult = fileUploadService.uploadManyImg(files, request);
            boolean isLegal = picUploadResult.isLegal();

            if (isLegal) {
                Map<String, List<String>> resMap = new HashMap<>();
                resMap.put("imgPaths", picUploadResult.getImgPaths());
                return CommonRsp.success(resMap);
            } else {
                return CommonRsp.error("图片上传有误");
            }
        } else {
            return CommonRsp.error("上传的图片格式必须为:bmp,jpg,jpeg,png");
        }
    }
}
