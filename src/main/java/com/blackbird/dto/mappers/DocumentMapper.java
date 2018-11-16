package com.blackbird.dto.mappers;

import com.blackbird.entities.Document;
import com.blackbird.dto.DocumentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

/**
 * Mapper class to conversion between @link Document and @link DocumentDto and vise versa
 */

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DocumentMapper extends GenericMapper<Document, DocumentDto> {
    @Mapping(target = "projectId", source = "entity.project.id")
    DocumentDto toDto(Document entity);
}
