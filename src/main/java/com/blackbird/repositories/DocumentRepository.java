package com.blackbird.repositories;

import com.blackbird.entities.Project;
import com.blackbird.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for operations with @link Document entity
 */

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByProject(Project project);
}
