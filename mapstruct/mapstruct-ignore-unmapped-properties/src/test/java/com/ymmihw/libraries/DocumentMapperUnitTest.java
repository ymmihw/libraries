package com.ymmihw.libraries;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.Date;
import org.junit.Test;
import com.ymmihw.libraries.mapper.DocumentMapper;
import com.ymmihw.libraries.model.Document;
import com.ymmihw.libraries.model.DocumentDTO;

public class DocumentMapperUnitTest {

  @Test
  public void givenDocumentEntityToDocumentDto_whenMaps_thenCorrect() {
    Document entity = new Document();
    entity.setId(1);
    entity.setTitle("Price 13-42");
    entity.setText("List of positions.......");
    entity.setModificationTime(new Date());

    DocumentDTO dto = DocumentMapper.INSTANCE.documentToDocumentDTO(entity);

    assertThat(dto.getId()).isEqualTo(entity.getId());
    assertThat(dto.getTitle()).isEqualTo(entity.getTitle());
    assertThat(dto.getText()).isEqualTo(entity.getText());
  }

  @Test
  public void givenDocumentDtoToDocumentEntity_whenMaps_thenCorrect() {
    DocumentDTO dto = new DocumentDTO();
    dto.setId(1);
    dto.setTitle("Price 13-42");
    dto.setText("List of positions.......");
    dto.setComments(Arrays.asList("Not all positions", "Wrong price values"));
    dto.setAuthor("Author1");

    Document entity = DocumentMapper.INSTANCE.documentDTOToDocument(dto);

    assertThat(entity.getId()).isEqualTo(dto.getId());
    assertThat(entity.getTitle()).isEqualTo(dto.getTitle());
    assertThat(entity.getText()).isEqualTo(dto.getText());
  }
}
