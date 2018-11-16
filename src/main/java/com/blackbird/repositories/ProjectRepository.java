package com.blackbird.repositories;

import com.blackbird.entities.Project;
import com.blackbird.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for operations with @link Project entity
 */

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByStatus(Status status);
}
