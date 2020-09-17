package com.chance.service.impl;

import com.chance.entity.Car;
import com.chance.common.converter.CarInfoConverter;
import com.chance.entity.dto.CarDto;
import com.chance.entity.enums.CarType;
import com.chance.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * <p>
 *
 * @author chance
 * @since 2020-09-16
 */
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarInfoConverter carInfoConverter;

    @Override
    public CarDto queryOneCar() {
        Car car = new Car("chance",2, CarType.SEDAN);
        CarDto carDto = carInfoConverter.carToCarDto(car);
        return carDto;
    }
}
