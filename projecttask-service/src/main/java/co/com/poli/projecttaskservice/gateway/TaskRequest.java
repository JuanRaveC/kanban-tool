package co.com.poli.projecttaskservice.gateway;

import co.com.poli.projecttaskservice.model.Backlog;
import co.com.poli.projecttaskservice.model.TaskStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
public class TaskRequest {
    @NotBlank
    private Long id; //notEmpty
    @NotBlank
    private String name; //notEmpty
    @NotBlank
    private String summary; //notEmpty
    private String acceptanceCriteria;
    private TaskStatus status;
    @Min(1)
    @Max(5)
    private Integer priority; //1-5
    @Min(1)
    @Max(8)
    private Double hours; // 1-8 positive
    private LocalDate startDate;
    private LocalDate endDate;
    private String projectIdentifier; // un-updatable
    private Backlog backlog; //manyToOne

}
