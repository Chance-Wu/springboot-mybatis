package com.chance.common.converter;

import com.chance.entity.User;
import com.chance.entity.dto.UserDto;
import org.mapstruct.Mapper;

/**
 * @author: chance
 * @date: 2024/8/29 00:03
 * @since: 1.0
 */
@Mapper(componentModel = "spring")
public interface UserConverter extends BaseConverter<User, UserDto> {

}
