package us.uplaw.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import us.uplaw.model.Document;
import us.uplaw.security.CurrentUser;
import us.uplaw.security.UserPrincipal;
import us.uplaw.service.DocumentService;

@RequestMapping("/documents")
public class DocumentController {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);

  @Autowired
  private DocumentService documentService;

  @PostMapping
  public ResponseEntity<Document> createDocument(@RequestBody final Document document) {
    Document response = documentService.createDocument(document);
    LOGGER.info("Created document with id {}", document.getId());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteDocument(@PathVariable final Long id) {
    documentService.deleteDocument(id);
    String message = "Deleted document with id " + id;
    LOGGER.info(message);
    return new ResponseEntity<>(message, HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<Document> updateDocument(@RequestBody final Document document) {
    Document response = documentService.updateDocument(document);
    LOGGER.info("Updated document with id {}", document.getId());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Document> getDocumentById(@CurrentUser UserPrincipal currentUser, @PathVariable final Long id) {
    Document document = documentService.getDocumentById(id);
    return new ResponseEntity<>(document, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Document>> listDocuments(@CurrentUser UserPrincipal currentUser) {
    List<Document> documents = documentService.listDocuments();
    return new ResponseEntity<>(documents, HttpStatus.OK);
  }

}
