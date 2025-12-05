package com.chance.mapper.mysql;

import com.chance.entity.User;
import com.chance.entity.dto.UserDto;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chance
 * @since 2020-08-22
 */
public interface UserMapper {

    Optional<List<UserDto>> queryAllUsers();

    void insertUser(User user);
}
