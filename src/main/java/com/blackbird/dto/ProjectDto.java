package com.blackbird.dto;

import com.blackbird.dto.groups.Existing;
import com.blackbird.dto.groups.New;
import com.blackbird.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

/**
 * Corresponding DTO class for @link Project entity
 */

@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name", "status"})
public class ProjectDto {
    @NotNull(
            groups = {Existing.class},
            message = "Id is required for existing Project"
    )
    @Min(
            groups = {Existing.class},
            value = 1,
            message = "Id should be positive number"
    )
    @Null(groups = {New.class})
    private Long id;
    @NotNull(
            groups = {New.class},
            message = "Project name is required for new Project"
    )
    private String name;
    private String description;
    private Status status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Null
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Null
    private LocalDateTime updatedAt;
}
