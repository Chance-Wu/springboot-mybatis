package com.chance.common.converter;

import com.chance.entity.Car;
import com.chance.entity.User;
import com.chance.entity.dto.CarDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * <p>
 * car信息转换器接口定义
 * <p>
 *
 * @author chance
 * @since 2020-09-16
 */
@Mapper(componentModel = "spring") //只有存在@Mapper注解才会将内部的接口方法自动实现
public interface CarInfoConverter {

    /**
     * 汽车数据实体转变为响应实体
     *
     * @param car
     * @return CarDto
     */
    @Mappings({
            @Mapping(source = "make", target = "name"),
            @Mapping(source = "numberOfSeats", target = "seatCount")
    })
    CarDto carToCarDto(Car car);


}
