package com.chance.common.converter;

import com.chance.entity.Car;
import com.chance.entity.dto.CarDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-31T10:12:43+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_292 (AdoptOpenJDK)"
)
@Component
public class CarInfoConverterImpl implements CarInfoConverter {

    @Override
    public CarDto carToCarDto(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDto carDto = new CarDto();

        carDto.setName( car.getMake() );
        carDto.setSeatCount( car.getNumberOfSeats() );
        if ( car.getType() != null ) {
            carDto.setType( car.getType().name() );
        }

        return carDto;
    }
}
