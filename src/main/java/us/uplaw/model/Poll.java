package us.uplaw.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import us.uplaw.model.audit.UserDateAudit;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Poll extends UserDateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 140)
  private String question;

  @OneToMany(
          mappedBy = "poll",
          cascade = CascadeType.ALL,
          fetch = FetchType.EAGER,
          orphanRemoval = true
  )
  @Size(min = 2, max = 6)
  @Fetch(FetchMode.SELECT)
  @BatchSize(size = 30)
  private List<Choice> choices = new ArrayList<>();

  @NotNull
  private Instant expirationDateTime;

  public void addChoice(Choice choice) {
    choices.add(choice);
    choice.setPoll(this);
  }

  public void removeChoice(Choice choice) {
    choices.remove(choice);
    choice.setPoll(null);
  }

}
