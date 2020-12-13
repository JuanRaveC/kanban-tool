package co.com.poli.projecttaskservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Backlog {

    private Long id;
    private String projectIdentifier; //notempty
    private Project project; //OneToOne
    private List<Task> projectTasks; //oneToMany

}
