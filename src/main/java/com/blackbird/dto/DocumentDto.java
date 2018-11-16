package com.blackbird.dto;

import com.blackbird.dto.groups.Existing;
import com.blackbird.dto.groups.New;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

/**
 * Corresponding DTO class for @link Document entity
 */

@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name", "projectId"})
public class DocumentDto {
    @NotNull(
            groups = {Existing.class},
            message = "Id is required for existing Document"
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
            message = "Document name is required for new Document"
    )
    private String name;
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ", timezone = "UTC")
    @Null
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ", timezone = "UTC")
    @Null
    private LocalDateTime updatedAt;
    private Long projectId;
}
