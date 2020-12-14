package co.com.poli.backlogservice.project;

import co.com.poli.backlogservice.model.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectClientFallback implements ProjectClient{
    @Override
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projectList = new ArrayList<>();
        Project project = Project.builder()
                .projectIdentifier("9999")
                .projectName("Fallback")
                .description("Fallback Response").build();
        projectList.add(project);
        return ResponseEntity.ok(projectList);
    }
}
