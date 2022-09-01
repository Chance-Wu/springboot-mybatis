package com.chance.mapper;

import com.chance.entity.User;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chance
 * @since 2020-08-22
 */
public interface UserMapper {

    List<User> queryAllUsers();
}
