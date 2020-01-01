package com.ymmihw.libraries.repo;

import com.ymmihw.libraries.domain.Store;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.CrudRepository;

@JaversSpringDataAuditable
public interface StoreRepository extends CrudRepository<Store, Integer> {
}
