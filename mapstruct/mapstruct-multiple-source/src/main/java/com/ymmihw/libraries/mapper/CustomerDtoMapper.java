package com.ymmihw.libraries.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.ymmihw.libraries.model.Customer;
import com.ymmihw.libraries.model.CustomerDto;

@Mapper
public interface CustomerDtoMapper {

  @Mapping(source = "firstName", target = "forename")
  @Mapping(source = "lastName", target = "surname")
  CustomerDto from(Customer customer);
}
