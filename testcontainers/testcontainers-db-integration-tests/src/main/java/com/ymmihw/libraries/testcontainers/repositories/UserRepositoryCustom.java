package com.ymmihw.libraries.testcontainers.repositories;

import java.util.List;
import java.util.Set;
import com.ymmihw.libraries.testcontainers.domain.User;

public interface UserRepositoryCustom {
  List<User> findByEmailIn(Set<String> emails);
}
