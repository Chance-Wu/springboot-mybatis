package com.chance.service.impl;

import com.chance.common.exception.BizException;
import com.chance.entity.User;
import com.chance.entity.dto.UserDto;
import com.chance.mapper.mysql.UserMapper;
import com.chance.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chance
 * @since 2020-08-22
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<UserDto> queryAllUsers() {
        log.info(">>>>>>>> queryAllUsers service");
        List<UserDto> userDtos = userMapper.queryAllUsers()
                .orElseThrow(() -> new BizException("10001", "查询用户列表失败"));
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserDto userDto : userDtos) {
            if (userDto.getAge() > 10) {
                userDtoList.add(userDto);
            }
        }

        List<Integer> collect = userDtos.stream().map(UserDto::getAge).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }
}
