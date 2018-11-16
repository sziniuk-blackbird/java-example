package com.blackbird.services;

import com.blackbird.dto.DocumentDto;
import com.blackbird.dto.mappers.DocumentMapper;
import com.blackbird.entities.Document;
import com.blackbird.entities.Project;
import com.blackbird.repositories.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for operations with @link Document
 */

@Slf4j
@Service
public class DocumentService {
    private final DocumentRepository repository;
    private final DocumentMapper documentMapper;

    @Autowired
    public DocumentService(DocumentRepository repository, DocumentMapper documentMapper) {
        this.repository = repository;
        this.documentMapper = documentMapper;
    }

    public Document create(Document document) {
        log.debug("Started Document creation name={}", document.getName());
        Document createdDocument = repository.save(document);
        log.debug("Created Document with id={}", createdDocument.getId());
        return createdDocument;
    }

    public List<Document> findAll() {
        log.debug("Looking for all Documents");
        List<Document> documents = repository.findAll();
        log.debug("Found {} documents", documents.size());
        return documents;
    }

    public List<DocumentDto> findAllByProject(Project project) {
        log.debug("Looking for all Documents by Project id={}, name={}", project.getId(), project.getName());
        List<Document> documents = repository.findAllByProject(project);
        log.debug("Found {} documents", documents.size());
        return documentMapper.toDtos(documents);
    }

    public Optional<Document> findById(Long id) {
        log.debug("Looking for Document with id {}", id);
        Optional<Document> foundDocument = repository.findById(id);
        foundDocument.ifPresentOrElse(
                doc -> log.debug("Found Document with name={}", doc.getName()),
                () -> log.debug("Document was not found")
        );
        return foundDocument;
    }

    public Document update(Document document) {
        log.debug("Started Document update id={}", document.getId());
        Document updatedDocument = repository.saveAndFlush(document);
        log.debug("Document was successfully updated");
        return updatedDocument;
    }

    public void delete(Document document) {
        log.debug("Started Document deletion id={}", document.getId());
        repository.delete(document);
        log.debug("Document was successfully deleted");
    }
}
