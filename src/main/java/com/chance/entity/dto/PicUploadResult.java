package com.chance.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * <p> PicUploadResult </p>
 *
 * @author chance
 * @date 2023/5/27 14:54
 * @since 1.0
 */
@Data
public class PicUploadResult {

    private boolean legal;

    private String imgPath;

    private List<String> imgPaths;
}
