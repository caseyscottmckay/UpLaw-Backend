package us.uplaw.service;

import java.util.List;
import us.uplaw.model.Document;

public interface DocumentService {

  Document createDocument(Document document);

  Document getDocumentById(Long id);

  List<Document> listDocuments();

  Document updateDocument(Document Document);

  void deleteDocument(Long id);
}
