package com.ymmihw.libraries.repo;

import com.ymmihw.libraries.domain.Product;
import org.javers.spring.annotation.JaversAuditable;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
  @Override
  @JaversAuditable
  <S extends Product> S save(S s);
}
