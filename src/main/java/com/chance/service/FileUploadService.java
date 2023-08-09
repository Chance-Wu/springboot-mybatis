package com.chance.service;

import com.chance.entity.dto.PicUploadResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> FileUploadService </p>
 *
 * @author chance
 * @date 2023/5/27 14:53
 * @since 1.0
 */
public interface FileUploadService {
    /**
     * 单图片上传
     *
     * @param uploadFile
     * @param request
     * @return
     */
    PicUploadResult uplodImg(MultipartFile uploadFile, HttpServletRequest request);

    /**
     * 多图片上传
     *
     * @param uploadFile
     * @param request
     * @return
     */
    PicUploadResult uploadManyImg(MultipartFile[] uploadFile, HttpServletRequest request);
}