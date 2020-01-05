package com.ymmihw.libraries.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.ymmihw.libraries.model.Car;
import com.ymmihw.libraries.model.CarDTO;

@Mapper
public interface CarMapper {
  CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

  CarDTO carToCarDTO(Car car);
}
