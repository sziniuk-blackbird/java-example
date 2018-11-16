package com.blackbird.dto.mappers;

import com.blackbird.dto.ProjectDto;
import com.blackbird.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * Mapper class to conversion between @link Project and @link ProjectDto and vise versa
 */

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProjectMapper extends GenericMapper<Project, ProjectDto> {
}
