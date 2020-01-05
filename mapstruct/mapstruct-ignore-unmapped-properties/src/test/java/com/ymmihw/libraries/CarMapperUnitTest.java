package com.ymmihw.libraries;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.ymmihw.libraries.mapper.CarMapper;
import com.ymmihw.libraries.model.Car;
import com.ymmihw.libraries.model.CarDTO;

public class CarMapperUnitTest {

  @Test
  public void givenCarEntitytoCar_whenMaps_thenCorrect() {

    Car entity = new Car();
    entity.setId(1);
    entity.setName("Toyota");

    CarDTO carDto = CarMapper.INSTANCE.carToCarDTO(entity);

    assertEquals(carDto.getId(), entity.getId());
    assertEquals(carDto.getName(), entity.getName());
  }
}
