package com.chance.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 *
 * <p>
 *
 * @author chance
 * @since 2020-08-25
 */
// chain=true，开启链式编程
@Accessors(chain = true, fluent = true)
@Data
public class DemoUser {

    private String username;
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birth;

    @Override
    public String toString() {
        return "DemoUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birth=" + birth +
                '}';
    }
}
