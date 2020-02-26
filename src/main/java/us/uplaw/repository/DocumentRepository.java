package us.uplaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.uplaw.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

}
