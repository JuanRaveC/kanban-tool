package co.com.poli.project.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Project {

    private Long id;
    private String projectName; //notEmpty - unique
    private String projectIdentifier; //notEmpty - unique - not updatable - 5-7 long
    private String description; //notEmpty
    private LocalDate startDate;
    private LocalDate endDate;
    private Backlog backlog; // OneToOne

}
