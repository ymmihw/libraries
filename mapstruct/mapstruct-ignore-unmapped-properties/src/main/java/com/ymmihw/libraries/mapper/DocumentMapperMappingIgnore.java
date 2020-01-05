package com.ymmihw.libraries.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.ymmihw.libraries.model.Document;
import com.ymmihw.libraries.model.DocumentDTO;

@Mapper
public interface DocumentMapperMappingIgnore {

  DocumentMapperMappingIgnore INSTANCE = Mappers.getMapper(DocumentMapperMappingIgnore.class);

  @Mapping(target = "comments", ignore = true)
  DocumentDTO documentToDocumentDTO(Document entity);

  @Mapping(target = "modificationTime", ignore = true)
  Document documentDTOToDocument(DocumentDTO dto);

}
