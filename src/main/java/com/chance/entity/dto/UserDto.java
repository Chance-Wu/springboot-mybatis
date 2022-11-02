package com.chance.entity.dto;

import com.chance.enums.SexEnum;
import lombok.Data;

/**
 * <p>
 *
 * <p>
 *
 * @author chance
 * @since 2020-08-22
 */
@Data
public class UserDto {

    private String username;
    private String password;
    private SexEnum userSex = SexEnum.DEFAULT;
    private String nickName;
}
