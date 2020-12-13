package co.com.poli.project.services;

import co.com.poli.project.dto.ProjectDto;

import java.util.List;

public interface ProjectService {
    List<ProjectDto> findAllProjects();
    ProjectDto createProject(ProjectDto projectDto);
}
