package us.uplaw.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.uplaw.model.Document;
import us.uplaw.repository.DocumentRepository;

@Service
public class DocumentServiceImpl implements DocumentService {

  private DocumentRepository documentRepository;

  @Autowired
  public void setDocumentRepository(DocumentRepository documentRepository) {
    this.documentRepository = documentRepository;
  }

  @Override
  public Document createDocument(Document document) {
    return documentRepository.save(document);
  }

  @Override
  public Document getDocumentById(Long id) {
    return documentRepository.getOne(id);
  }

  @Override
  public List<Document> listDocuments() {
    return documentRepository.findAll();
  }

  @Override
  public Document updateDocument(Document document) {
    return documentRepository.save(document);
  }

  @Override
  public void deleteDocument(Long id) {
    documentRepository.deleteById(id);
  }


}
