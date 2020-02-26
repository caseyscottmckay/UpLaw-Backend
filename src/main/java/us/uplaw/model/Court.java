package us.uplaw.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Court {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String courtName;

  private String courtAbbreviation;

  private String courtCitationAbbreviation;

  private String courtHomepage;

  private LocalDate courtStartDate;

  private LocalDate courtEndDate;

  private String courtJurisdiction;


}
