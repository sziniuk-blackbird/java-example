package com.blackbird.controllers;

import com.blackbird.dto.DocumentDto;
import com.blackbird.dto.groups.Existing;
import com.blackbird.dto.groups.New;
import com.blackbird.dto.mappers.DocumentMapper;
import com.blackbird.entities.Document;
import com.blackbird.services.DocumentService;
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
 * Rest controller for operations with @link Document.
 * Implements CRUD operations
 */

@RestController
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentMapper documentMapper;

    @Autowired
    public DocumentController(DocumentService documentService, DocumentMapper documentMapper) {
        this.documentService = documentService;
        this.documentMapper = documentMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentDto createDocument(@Validated(New.class) @RequestBody DocumentDto dto) {
        Document createdDocument = documentService.create(documentMapper.fromDto(dto));
        return documentMapper.toDto(createdDocument);
    }

    @GetMapping("/{id}")
    public ResponseEntity getDocument(@NotNull @Min(1) @PathVariable("id") Long id) {
        Optional<Document> foundDocument = documentService.findById(id);
        return foundDocument.map(doc -> ResponseEntity.ok(documentMapper.toDto(doc)))
                .orElseGet(this::documentNotFoundResponse);
    }

    @GetMapping
    public List<DocumentDto> getAll() {
        List<Document> documents = documentService.findAll();
        return documentMapper.toDtos(documents);
    }

    @PutMapping
    public ResponseEntity updateDocument(@Validated(Existing.class) @RequestBody DocumentDto dto) {
        Optional<Document> foundDocument = documentService.findById(dto.getId());
        return foundDocument.map(doc -> {
            documentMapper.updateFromDto(dto, doc);
            Document updatedDocument = documentService.update(doc);
            return ResponseEntity.ok(documentMapper.toDto(updatedDocument));
        }).orElseGet(this::documentNotFoundResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDocument(@NotNull @Min(1) @PathVariable("id") Long id) {
        Optional<Document> foundDocument = documentService.findById(id);
        return foundDocument.map(doc -> {
            documentService.delete(doc);
            return ResponseEntity.ok().build();
        }).orElseGet(this::documentNotFoundResponse);
    }
    
    private ResponseEntity documentNotFoundResponse() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found");
    }
}

