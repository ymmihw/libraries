package com.ymmihw.libraries.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.ymmihw.libraries.model.Document;
import com.ymmihw.libraries.model.DocumentDTO;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface DocumentMapperWithConfig {

  DocumentMapperWithConfig INSTANCE = Mappers.getMapper(DocumentMapperWithConfig.class);

  DocumentDTO documentToDocumentDTO(Document entity);

  Document documentDTOToDocument(DocumentDTO dto);
}
