package com.chance.controller;

import com.chance.entity.dto.CarDto;
import com.chance.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * <p>
 *
 * @author chance
 * @since 2020-09-16
 */
@RestController
@RequestMapping("/mapstruct")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/getCar")
    public CarDto getCar() {
        CarDto carDto = carService.queryOneCar();
        return carDto;
    }

}
