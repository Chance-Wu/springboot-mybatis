package com.chance.service;

import com.chance.entity.User;
import com.chance.entity.dto.UserDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chance
 * @since 2020-08-22
 */
public interface IUserService {

    List<UserDto> queryAllUsers();

    void insertUser(User user);
}
