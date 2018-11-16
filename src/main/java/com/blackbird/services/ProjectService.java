package com.blackbird.services;

import com.blackbird.dto.DocumentDto;
import com.blackbird.entities.Project;
import com.blackbird.enums.Status;
import com.blackbird.repositories.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for operations with @link Project
 */

@Slf4j
@Service
public class ProjectService {
    private final ProjectRepository repository;
    private final DocumentService documentService;

    @Autowired
    public ProjectService(ProjectRepository repository, DocumentService documentService) {
        this.repository = repository;
        this.documentService = documentService;
    }

    public Project create(Project project) {
        log.debug("Started Project creation name={}", project.getName());
        Project createdProject = repository.save(project);
        log.debug("Created Project with id={}", createdProject.getId());
        return createdProject;
    }

    public List<Project> findAll() {
        log.debug("Looking for all Projects");
        List<Project> projects = repository.findAll();
        log.debug("Found {} projects", projects.size());
        return projects;
    }

    public List<Project> findAllActive() {
        log.debug("Looking for all Active Projects");
        List<Project> activeProjects = repository.findAllByStatus(Status.ACTIVE);
        log.debug("Found {} active projects", activeProjects.size());
        return activeProjects;
    }

    public Optional<Project> findById(Long id) {
        log.debug("Looking for Project with id {}", id);
        Optional<Project> foundProject = repository.findById(id);
        foundProject.ifPresentOrElse(
                proj -> log.debug("Found Project with name={} status={}", proj.getName(), proj.getStatus()),
                () -> log.debug("Project was not found")
        );
        return foundProject;
    }

    public Project update(Project project) {
        log.debug("Started Project update id={}", project.getId());
        Project updatedProject = repository.saveAndFlush(project);
        log.debug("Project was successfully updated");
        return updatedProject;
    }

    public void delete(Project project) {
        log.debug("Started Project deletion id={}", project.getId());
        repository.delete(project);
        log.debug("Project was successfully deleted");
    }

    public List<DocumentDto> findDocuments(Project project) {
        return documentService.findAllByProject(project);
    }
}
