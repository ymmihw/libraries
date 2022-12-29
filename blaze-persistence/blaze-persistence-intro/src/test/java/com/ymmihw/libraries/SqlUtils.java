package com.ymmihw.libraries;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
@Transactional
@AllArgsConstructor
public class SqlUtils {

  private final EntityManager em;

  public void execute(String sql) {
    Query nativeQuery = em.createNativeQuery(sql);
    nativeQuery.executeUpdate();
  }
}
