package co.com.poli.project.services;

import co.com.poli.project.dto.ProjectDto;
import co.com.poli.project.model.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private List<Project> projectList;

    public ProjectServiceImpl() {
        this.projectList = new ArrayList<>();
    }

    @Override
    public List<ProjectDto> findAllProjects() {
        return projectList.stream()
                .map(project -> ProjectDto.builder()
                        .projectIdentifier(project.getProjectIdentifier())
                        .id(project.getId())
                        .projectName(project.getProjectName())
                        .description(project.getDescription())
                        .startDate(project.getStartDate())
                        .endDate(project.getEndDate())
                        .backlog(project.getBacklog())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        if(validateProjectExistence(projectDto.getProjectIdentifier()).isEmpty()){
            Random rd = new Random();
            Long idGenerated = rd.nextLong();
            projectList.add(Project.builder()
                    .id(idGenerated)
                    .projectIdentifier(projectDto.getProjectIdentifier())
                    .projectName(projectDto.getProjectName())
                    .description(projectDto.getDescription())
                    .startDate(projectDto.getStartDate() != null ? projectDto.getStartDate() : null)
                    .endDate(projectDto.getEndDate() != null ? projectDto.getEndDate() : null)
                    .backlog(projectDto.getBacklog() != null ? projectDto.getBacklog() : null)
                    .build());
            projectDto.setId(idGenerated);
        }
        return projectDto;
    }

    private String validateProjectExistence(String id){
        return projectList.stream()
                .map(Project::getProjectIdentifier)
                .filter(p -> p.contains(id))
                .findFirst()
                .orElse("");
    }
}
