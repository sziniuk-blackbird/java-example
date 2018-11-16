package com.blackbird.dto.mappers;

import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Generic interface for entity-dto mappers that specifies basic mappings
 *
 * @param <E> entity class
 * @param <D> DTO class
 */

public interface GenericMapper<E, D> {
    E fromDto(D dto);
    D toDto(E entitiy);
    List<E> fromDtos(List<D> dtos);
    List<D> toDtos(List<E> entity);
    void updateFromDto(D dto, @MappingTarget E entity);
}
