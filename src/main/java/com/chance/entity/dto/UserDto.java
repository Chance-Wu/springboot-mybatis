package com.chance.entity.dto;

import com.chance.common.annotation.CustomSerializer;
import com.chance.common.annotation.sensitive.UserNameRule;
import com.chance.common.enums.SexEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.TimeZone;

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

    /**
     * 脱敏
     */
    @CustomSerializer(value = UserNameRule.class)
    private String username;
    private String password;
    private SexEnum userSex = SexEnum.DEFAULT;
    private String nickName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
