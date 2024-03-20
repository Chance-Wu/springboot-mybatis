package com.chance.entity.vo;

import com.chance.common.validator.IdentityCardNumber;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * <p>
 *
 * @author chance
 * @since 2020-09-03
 */
@Data
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CourseRequest implements Serializable {

    private static final long serialVersionUID = -6974651285970774558L;

    @NotBlank(message = "编号不能为空")
    private String cNo;
    @NotBlank(message = "名称不能为空")
    private String cName;

    @AssertTrue
    private boolean isTrue;

    @Size(min = 1,max = 4)
    private String[] projects;

    @NotBlank(message = "code不能为空")
    @Length(min = 8,max = 16,message = "code长度不能低于8个字符，不能长于16个字符")
    private String code;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String mobile;

    @Past
    private Date startTime;
    @Future
    private Date endTime;

    @NotBlank(message = "联系邮箱不能为空")
    @Email(message = "邮箱格式不对")
    private String email;

    @NotBlank(message = "身份证号不能为空")
    @IdentityCardNumber(message = "身份证信息有误,请核对后提交")
    private String clientCardNo;
}
