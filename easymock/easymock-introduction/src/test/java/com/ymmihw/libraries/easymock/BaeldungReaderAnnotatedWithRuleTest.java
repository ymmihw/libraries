package com.ymmihw.libraries.easymock;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import java.util.NoSuchElementException;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(EasyMockExtension.class)
public class BaeldungReaderAnnotatedWithRuleTest {

  @Mock
  ArticleReader mockArticleReader;

  @Mock
  IArticleWriter mockArticleWriter;

  @TestSubject
  BaeldungReader baeldungReader = new BaeldungReader();

  @Test
  public void givenBaeldungReader_whenReadNext_thenNextArticleRead() {
    expect(mockArticleReader.next()).andReturn(null);
    replay(mockArticleReader);
    baeldungReader.readNext();
    verify(mockArticleReader);
  }

  @Mock
  BaeldungReader mockBaeldungReader;

  @Test
  public void givenBaeldungReader_whenWrite_thenWriterCalled() {
    expect(mockArticleWriter.write("title", "content")).andReturn(null);
    replay(mockArticleWriter);
    baeldungReader.write("title", "content");
    verify(mockArticleWriter);
  }

  @Test
  public void givenArticlesInReader_whenReadTillEnd_thenThrowException() {
    expect(mockArticleReader.next()).andReturn(null).times(2)
        .andThrow(new NoSuchElementException());
    replay(mockArticleReader);
    try {
      for (int i = 0; i < 3; i++) {
        baeldungReader.readNext();
      }
    } catch (Exception ignored) {
    }
    verify(mockArticleReader);
  }

}
