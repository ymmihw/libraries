package com.ymmihw.libraries.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.ymmihw.libraries.App;
import com.ymmihw.libraries.entity.Person;

@SpringBootTest(classes = App.class)
@Transactional
public class PersonDaoIntegrationTest {

  @Autowired
  private PersonDao personDao;

  @Test
  public void testCreation() {
    personDao.save(new Person("Erich", "Gamma"));
    final Person person = new Person("Kent", "Beck");
    personDao.save(person);
    personDao.save(new Person("Ralph", "Johnson"));

    final Person personFromDb = personDao.findPersonsByFirstnameQueryDSL("Kent").get(0);
    assertEquals(person.getId(), personFromDb.getId());
  }

  @Test
  public void testMultipleFilter() {
    personDao.save(new Person("Erich", "Gamma"));
    final Person person = personDao.save(new Person("Ralph", "Beck"));
    final Person person2 = personDao.save(new Person("Ralph", "Johnson"));

    final Person personFromDb =
        personDao.findPersonsByFirstnameAndSurnameQueryDSL("Ralph", "Johnson").get(0);
    assertNotSame(person.getId(), personFromDb.getId());
    assertEquals(person2.getId(), personFromDb.getId());
  }

  @Test
  public void testOrdering() {
    final Person person = personDao.save(new Person("Kent", "Gamma"));
    personDao.save(new Person("Ralph", "Johnson"));
    final Person person2 = personDao.save(new Person("Kent", "Zivago"));

    final Person personFromDb =
        personDao.findPersonsByFirstnameInDescendingOrderQueryDSL("Kent").get(0);
    assertNotSame(person.getId(), personFromDb.getId());
    assertEquals(person2.getId(), personFromDb.getId());
  }

  @Test
  public void testMaxAge() {
    personDao.save(new Person("Kent", "Gamma", 20));
    personDao.save(new Person("Ralph", "Johnson", 35));
    personDao.save(new Person("Kent", "Zivago", 30));

    final int maxAge = personDao.findMaxAge();
    assertTrue(maxAge == 35);
  }

  @Test
  public void testMaxAgeByName() {
    personDao.save(new Person("Kent", "Gamma", 20));
    personDao.save(new Person("Ralph", "Johnson", 35));
    personDao.save(new Person("Kent", "Zivago", 30));

    final Map<String, Integer> maxAge = personDao.findMaxAgeByName();
    assertTrue(maxAge.size() == 2);
    assertSame(35, maxAge.get("Ralph"));
    assertSame(30, maxAge.get("Kent"));
  }
}
