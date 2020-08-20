package com.chance.service.Impl;

import com.chance.entity.User;
import com.chance.mapper.UserMapper;
import com.chance.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *
 * <p>
 *
 * @author chance
 * @since 2020-08-20
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public String queryUserById(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user.getUsername();
    }
}
