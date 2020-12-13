package co.com.poli.project.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProjectTask {

    private Long id; //notEmpty
    private String name; //notEmpty
    private String summary; //notEmpty
    private String acceptanceCriteria;
    private TaskStatus status;
    private Integer priority; //1-5
    private Double hours; // 1-8 positive
    private LocalDate startDate;
    private LocalDate endDate;
    private String projectIdentifier; // un-updatable
    private Backlog backlog; //manyToOne

}
