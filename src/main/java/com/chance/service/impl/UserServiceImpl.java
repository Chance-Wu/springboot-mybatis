package com.chance.service.impl;

import com.chance.entity.User;
import com.chance.entity.dto.UserDto;
import com.chance.mapper.UserMapper;
import com.chance.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chance
 * @since 2020-08-22
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDto> queryAllUsers() {
        return userMapper.queryAllUsers();
    }

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }
}
