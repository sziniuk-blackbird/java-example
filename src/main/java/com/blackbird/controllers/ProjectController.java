package com.blackbird.controllers;

import com.blackbird.dto.DocumentDto;
import com.blackbird.dto.ProjectDto;
import com.blackbird.dto.groups.Existing;
import com.blackbird.dto.groups.New;
import com.blackbird.dto.mappers.ProjectMapper;
import com.blackbird.entities.Project;
import com.blackbird.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Rest controller for operations with @link Project.
 */

@RestController
@RequestMapping("/project")
@Validated
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(@Validated(New.class) @RequestBody ProjectDto dto) {
        Project createdProject = projectService.create(projectMapper.fromDto(dto));
        return projectMapper.toDto(createdProject);
    }

    @GetMapping("/{id}")
    public ResponseEntity getProject(@NotNull @Min(1) @PathVariable("id") Long id) {
        Optional<Project> foundProject = projectService.findById(id);
        return foundProject.map(proj -> ResponseEntity.ok(projectMapper.toDto(proj)))
                .orElseGet(this::projectNotFoundResponse);
    }

    @GetMapping
    public List<ProjectDto> getAll() {
        List<Project> projects = projectService.findAll();
        return projectMapper.toDtos(projects);
    }

    @GetMapping("/active")
    public List<ProjectDto> getAllActive() {
        List<Project> projects = projectService.findAllActive();
        return projectMapper.toDtos(projects);
    }

    @GetMapping("/{id}/documents")
    public ResponseEntity getDocuments(@NotNull @Min(1) @PathVariable("id") Long id) {
        Optional<Project> foundProject = projectService.findById(id);
        return foundProject.map(proj -> {
            List<DocumentDto> documents = projectService.findDocuments(proj);
            return ResponseEntity.ok(documents);
        }).orElseGet(this::projectNotFoundResponse);
    }

    @PutMapping
    public ResponseEntity updateProject(@Validated(Existing.class) @RequestBody ProjectDto dto) {
        Optional<Project> foundProject = projectService.findById(dto.getId());
        return foundProject.map(proj -> {
            projectMapper.updateFromDto(dto, proj);
            Project updatedProject = projectService.update(proj);
            return ResponseEntity.ok(projectMapper.toDto(updatedProject));
        }).orElseGet(this::projectNotFoundResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProject(@NotNull @Min(1) @PathVariable("id") Long id) {
        Optional<Project> foundProject = projectService.findById(id);
        return foundProject.map(proj -> {
            projectService.delete(proj);
            return ResponseEntity.ok().build();
        }).orElseGet(this::projectNotFoundResponse);
    }
    
    private ResponseEntity projectNotFoundResponse() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
    }
}

