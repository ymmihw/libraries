package com.ymmihw.libraries.debezium.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ymmihw.libraries.debezium.entity.Customer;
import com.ymmihw.libraries.debezium.repository.CustomerRepository;
import io.debezium.data.Envelope.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  public void replicateData(Map<String, Object> customerData, Operation operation) {
    final ObjectMapper mapper = new ObjectMapper();
    final Customer customer = mapper.convertValue(customerData, Customer.class);

    if (Operation.DELETE.name().equals(operation.name())) {
      customerRepository.deleteById(customer.getId());
    } else {
      customerRepository.save(customer);
    }
  }
}
