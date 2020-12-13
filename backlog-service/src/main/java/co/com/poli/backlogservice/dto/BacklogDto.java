package co.com.poli.backlogservice.dto;

import co.com.poli.backlogservice.model.Project;
import co.com.poli.backlogservice.model.ProjectTask;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BacklogDto {
    private Long id;
    private String projectIdentifier; //notempty
    private Project project; //OneToOne
    private List<ProjectTask> projectTasks; //oneToMany
}
