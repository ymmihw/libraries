package com.ymmihw.libraries.view;

import com.ymmihw.libraries.model.Post;
import com.blazebit.persistence.view.EntityView;

@EntityView(Post.class)
public interface PostWithAuthorView extends PostView {
  PersonView getAuthor();
}
