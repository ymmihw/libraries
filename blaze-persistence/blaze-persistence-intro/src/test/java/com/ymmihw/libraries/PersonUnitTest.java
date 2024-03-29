package com.ymmihw.libraries;

import com.ymmihw.libraries.model.Person;
import com.ymmihw.libraries.model.Post;
import com.ymmihw.libraries.repository.PersonRepository;
import com.ymmihw.libraries.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = TestContextConfig.class)
@ExtendWith(SpringExtension.class)
@Transactional
public class PersonUnitTest {

  @Autowired private PersonRepository personRepository;

  @Test
  public void whenFind_thenReturnCorrectListSize() {
    final Iterable<Person> listIterable = personRepository.find();
    final List<Person> list = new ArrayList<>();
    listIterable.forEach(list::add);
    assertEquals(2, list.size());
    assertEquals("John", list.get(0).getName());
  }

  @Test
  public void whenFindAll_thenReturnCorrectListSize() {
    final Iterable<Person> listIterable = personRepository.findAll();
    final List<Person> list = new ArrayList<>();
    listIterable.forEach(list::add);
    assertEquals(3, list.size());
  }

  @Test
  public void whenFindPostsByPerson_thenReturnCorrectListSize() {
    final Iterable<Post> listIterable = personRepository.findPostsByPerson();
    final List<Post> list = new ArrayList<>();
    listIterable.forEach(list::add);
    assertEquals(7, list.size());
  }
}
