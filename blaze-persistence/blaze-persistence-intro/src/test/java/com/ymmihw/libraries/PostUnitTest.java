package com.ymmihw.libraries;

import com.ymmihw.libraries.model.Post;
import com.ymmihw.libraries.repository.PostRepository;
import com.ymmihw.libraries.repository.PostViewRepository;
import com.ymmihw.libraries.view.PostView;
import com.ymmihw.libraries.view.PostWithAuthorView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = TestContextConfig.class)
@ExtendWith(SpringExtension.class)
public class PostUnitTest {

  @Autowired private PostViewRepository postViewRepository;

  @Autowired private PostRepository postRepository;

  @Test
  public void whenFindAll_thenReturnCorrectListViewSize() {
    final Iterable<PostWithAuthorView> listIterable = postViewRepository.findAll();
    final List<PostView> list = new ArrayList<>();
    listIterable.forEach(list::add);
    assertEquals(7, list.size());
  }

  @Test
  public void givenPostIdAndAuthorName_whenFind_thenReturnCorrectResult() {
    final Iterable<PostWithAuthorView> listIterable = postRepository.findBy("Spring", "Peter");
    final List<PostView> list = new ArrayList<>();
    listIterable.forEach(list::add);
    assertEquals(4, list.size());
  }

  @Test
  public void whenFindAll_thenReturnCorrectListSize() {
    final Iterable<Post> listIterable = postRepository.findAll();
    final List<Post> list = new ArrayList<>();
    listIterable.forEach(list::add);
    assertEquals(7, list.size());
  }
}
