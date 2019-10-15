package com.ymmihw.libraries.liquibase.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository
    extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

}
