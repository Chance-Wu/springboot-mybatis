package com.chance.controller;

import com.chance.common.api.CommonRsp;
import com.chance.entity.dto.PicUploadResult;
import com.chance.service.FileUploadService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> FileUploadController </p>
 *
 * @author chance
 * @date 2023/5/27 15:30
 * @since 1.0
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {

    // 允许上传的格式 图片形式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".png"};

    @Resource
    private FileUploadService fileUploadService;

    @PostMapping("/uploadImg")
    public CommonRsp uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        boolean isFlag = false;
        for (String type : IMAGE_TYPE) {
            System.out.println(file.getOriginalFilename());
            if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
                isFlag = true;
                break;
            }
        }

        if (isFlag) {
            PicUploadResult picUploadResult = fileUploadService.uplodImg(file, request);
            boolean isLegal = picUploadResult.isLegal();

            if (isLegal) {
                Map resMap = new HashMap<>();
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
    public CommonRsp uploadManyImg(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
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
                Map resMap = new HashMap<>();
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
