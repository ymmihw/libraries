package com.ymmihw.libraries.ignite.spring.repository;

import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;
import org.springframework.stereotype.Repository;
import com.ymmihw.libraries.ignite.spring.dto.EmployeeDTO;

@Repository
@RepositoryConfig(cacheName = "baeldungCache")
public interface EmployeeRepository extends IgniteRepository<EmployeeDTO, Integer> {

  EmployeeDTO getEmployeeDTOById(Integer id);
}
