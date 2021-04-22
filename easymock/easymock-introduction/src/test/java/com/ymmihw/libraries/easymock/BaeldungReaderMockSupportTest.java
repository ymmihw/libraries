package com.ymmihw.libraries.easymock;

import static org.easymock.EasyMock.expect;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.NoSuchElementException;
import org.easymock.EasyMockExtension;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(EasyMockExtension.class)
public class BaeldungReaderMockSupportTest extends EasyMockSupport {

  @TestSubject
  BaeldungReader baeldungReader = new BaeldungReader();
  @Mock
  ArticleReader mockArticleReader;
  @Mock
  IArticleWriter mockArticleWriter;

  @Test
  public void givenBaeldungReader_whenReadAndWriteSequencially_thenWorks() {
    expect(mockArticleReader.next()).andReturn(null).times(2)
        .andThrow(new NoSuchElementException());
    expect(mockArticleWriter.write("title", "content")).andReturn("BAEL-201801");
    replayAll();

    Exception expectedException = null;
    try {
      for (int i = 0; i < 3; i++) {
        baeldungReader.readNext();
      }
    } catch (Exception exception) {
      expectedException = exception;
    }
    String articleId = baeldungReader.write("title", "content");
    verifyAll();
    assertEquals(NoSuchElementException.class, expectedException.getClass());
    assertEquals("BAEL-201801", articleId);
  }

}
