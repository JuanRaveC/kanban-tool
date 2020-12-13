package co.com.poli.backlogservice.gateway;

import co.com.poli.backlogservice.model.Project;
import co.com.poli.backlogservice.model.ProjectTask;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
@Data
@Builder
public class BacklogRequest {
    @NotBlank
    private String projectIdentifier; //notempty
    private Project project; //OneToOne
    private List<ProjectTask> projectTasks; //oneToMany
}
