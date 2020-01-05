package com.ymmihw.libraries.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import com.ymmihw.libraries.model.Document;
import com.ymmihw.libraries.model.DocumentDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentMapperUnmappedPolicy {

  DocumentMapperUnmappedPolicy INSTANCE = Mappers.getMapper(DocumentMapperUnmappedPolicy.class);

  DocumentDTO documentToDocumentDTO(Document entity);

  Document documentDTOToDocument(DocumentDTO dto);
}
