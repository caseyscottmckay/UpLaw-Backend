package us.uplaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.uplaw.model.Court;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {

}
