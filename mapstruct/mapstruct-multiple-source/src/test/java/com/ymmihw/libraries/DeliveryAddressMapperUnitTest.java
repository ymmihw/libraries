package com.ymmihw.libraries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import com.ymmihw.libraries.mapper.DeliveryAddressMapper;
import com.ymmihw.libraries.model.Address;
import com.ymmihw.libraries.model.Customer;
import com.ymmihw.libraries.model.DeliveryAddress;

public class DeliveryAddressMapperUnitTest {

  private DeliveryAddressMapper deliveryAddressMapper =
      Mappers.getMapper(DeliveryAddressMapper.class);

  @Test
  public void testGivenCustomerAndAddress_mapsToDeliveryAddress() {

    // given a customer
    Customer customer = Customer.builder().firstName("Max").lastName("Powers").build();

    // and some address
    Address homeAddress =
        Address.builder().street("123 Some Street").county("Nevada").postalcode("89123").build();

    // when calling DeliveryAddressMapper::from
    DeliveryAddress deliveryAddress = deliveryAddressMapper.from(customer, homeAddress);

    // then a new DeliveryAddress is created, based on the given customer and his home address
    assertEquals(deliveryAddress.getForename(), customer.getFirstName());
    assertEquals(deliveryAddress.getSurname(), customer.getLastName());
    assertEquals(deliveryAddress.getStreet(), homeAddress.getStreet());
    assertEquals(deliveryAddress.getCounty(), homeAddress.getCounty());
    assertEquals(deliveryAddress.getPostalcode(), homeAddress.getPostalcode());

  }

  @Test
  public void testGivenDeliveryAddressAndSomeOtherAddress_updatesDeliveryAddress() {

    // given a delivery address
    DeliveryAddress deliveryAddress = DeliveryAddress.builder().forename("Max").surname("Powers")
        .street("123 Some Street").county("Nevada").postalcode("89123").build();

    // and some new address
    Address newAddress = Address.builder().street("456 Some other street").county("Arizona")
        .postalcode("12345").build();

    // when calling DeliveryAddressMapper::updateAddress
    DeliveryAddress updatedDeliveryAddress =
        deliveryAddressMapper.updateAddress(deliveryAddress, newAddress);

    // then the *existing* delivery address is updated
    assertSame(deliveryAddress, updatedDeliveryAddress);

    assertEquals(deliveryAddress.getStreet(), newAddress.getStreet());
    assertEquals(deliveryAddress.getCounty(), newAddress.getCounty());
    assertEquals(deliveryAddress.getPostalcode(), newAddress.getPostalcode());

  }
}
