package com.chance.entity.dto;

import com.chance.common.annotation.CustomSerializer;
import com.chance.common.annotation.sensitive.UserNameRule;
import com.chance.common.enums.SexEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 *
 * <p>
 *
 * @author chance
 * @since 2020-08-22
 */
@Schema(name = "用户信息")
@Data
public class UserDto {

    /**
     * 脱敏
     */
    @CustomSerializer(value = UserNameRule.class)
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "性别")
    private String userSex = SexEnum.DEFAULT.getCode();

    @Schema(description = "别称")
    private String nickName;

    @Schema(description = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "生日不能为空")
    private Date birthDate;
}
