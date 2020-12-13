package co.com.poli.project.gateway;

import co.com.poli.project.model.Backlog;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class ProjectRequest {
    @NotBlank
    private String projectName; //notEmpty - unique
    @NotBlank
    private String projectIdentifier; //notEmpty - unique - not updatable - 5-7 long
    @NotBlank
    private String description; //notEmpty
    private LocalDate startDate;
    private LocalDate endDate;
    private Backlog backlog; // OneToOne
}
