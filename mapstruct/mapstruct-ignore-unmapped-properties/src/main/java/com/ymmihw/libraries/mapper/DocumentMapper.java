package com.ymmihw.libraries.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.ymmihw.libraries.model.Document;
import com.ymmihw.libraries.model.DocumentDTO;

@Mapper
public interface DocumentMapper {

  DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

  DocumentDTO documentToDocumentDTO(Document entity);

  Document documentDTOToDocument(DocumentDTO dto);
}
