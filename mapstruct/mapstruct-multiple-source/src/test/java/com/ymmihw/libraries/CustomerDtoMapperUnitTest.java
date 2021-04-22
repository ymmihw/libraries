package com.ymmihw.libraries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import com.ymmihw.libraries.mapper.CustomerDtoMapper;
import com.ymmihw.libraries.model.Customer;
import com.ymmihw.libraries.model.CustomerDto;

public class CustomerDtoMapperUnitTest {

  private CustomerDtoMapper customerDtoMapper = Mappers.getMapper(CustomerDtoMapper.class);

  @Test
  void testGivenCustomer_mapsToCustomerDto() {

    // given
    Customer customer = Customer.builder().firstName("Max").lastName("Powers").build();

    // when
    CustomerDto customerDto = customerDtoMapper.from(customer);

    // then
    assertEquals(customerDto.getForename(), customer.getFirstName());
    assertEquals(customerDto.getSurname(), customer.getLastName());
  }
}
