package co.com.poli.backlogservice.service;

import co.com.poli.backlogservice.dto.BacklogDto;
import co.com.poli.backlogservice.model.Backlog;
import co.com.poli.backlogservice.model.Project;
import co.com.poli.backlogservice.project.ProjectClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@Slf4j
public class BacklogServiceImpl implements BacklogService{

    private List<Backlog> backlogs ;
    private final ProjectClient projectClient;

    public BacklogServiceImpl(@Qualifier("project-service") ProjectClient projectClient) {
        this.backlogs = new ArrayList<>();
        this.projectClient = projectClient;
    }

    @Override
    public BacklogDto createBacklog(BacklogDto backlogDto) {
        List<Project> projectsFound = new ArrayList<>();
        try {
            projectsFound.addAll(Objects.requireNonNull(projectClient.getAllProjects().getBody()));
            if (validateProjectExistence(projectsFound, backlogDto.getProjectIdentifier()).isEmpty()) {
                Random rd = new Random();
                Long idGenerated = rd.nextLong();
                backlogs.add(Backlog.builder()
                        .id(idGenerated)
                        .projectIdentifier(backlogDto.getProjectIdentifier())
                        .project(backlogDto.getProject() != null ? backlogDto.getProject() : null)
                        .projectTasks(backlogDto.getProjectTasks() != null ? backlogDto.getProjectTasks() : null)
                        .build());
                backlogDto.setId(idGenerated);
            }
            return backlogDto;
        }catch (NullPointerException e){
            log.info("Error fetching projects from service");
            return backlogDto;
        }
    }

    private String validateProjectExistence(List<Project> projectsFound, String id){
        return projectsFound.stream()
                .map(Project::getProjectIdentifier)
                .filter(p -> p.contains(id))
                .findFirst()
                .orElse("");
    }
}
