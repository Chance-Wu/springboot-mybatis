package com.chance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author chance
 * @since 2020-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private String userSex;

    private String nickName;
}
