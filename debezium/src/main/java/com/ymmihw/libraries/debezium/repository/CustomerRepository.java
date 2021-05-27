package com.ymmihw.libraries.debezium.repository;

import com.ymmihw.libraries.debezium.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {}
