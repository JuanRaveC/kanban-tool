package co.com.poli.projecttaskservice.service;

import co.com.poli.projecttaskservice.dto.TaskDto;
import co.com.poli.projecttaskservice.model.Project;
import co.com.poli.projecttaskservice.model.Task;
import co.com.poli.projecttaskservice.model.TaskStatus;
import co.com.poli.projecttaskservice.project.ProjectClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private List<Task> taskList;
    private final ProjectClient client;

    public TaskServiceImpl(ProjectClient client) {
        this.taskList = new ArrayList<>();
        this.client = client;
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        List<Project> projectsFound = new ArrayList<>();
        try {
            projectsFound.addAll(Objects.requireNonNull(client.getAllProjects().getBody()));
            if (validateProjectExistence(projectsFound, taskDto.getProjectIdentifier()).isEmpty()) {
                Random rd = new Random();
                Long idGenerated = rd.nextLong();
                taskList.add(Task.builder()
                        .id(idGenerated)
                        .projectIdentifier(taskDto.getProjectIdentifier())
                        .acceptanceCriteria(taskDto.getAcceptanceCriteria())
                        .name(taskDto.getName())
                        .summary(taskDto.getSummary())
                        .priority(taskDto.getPriority())
                        .startDate(taskDto.getStartDate())
                        .endDate(taskDto.getEndDate())
                        .hours(taskDto.getHours())
                        .status(taskDto.getStatus())
                        .backlog(taskDto.getBacklog())
                        .build());
                taskDto.setId(idGenerated);
            }
            return taskDto;
        } catch (NullPointerException e) {
            log.info("Error fetching projects from service");
            return taskDto;
        }
    }

    @Override
    public List<TaskDto> getAllTaskByProject(String id) {
        return taskList.stream()
                .filter(task -> task.getProjectIdentifier().equals(id))
                .map(task -> TaskDto.builder()
                        .id(task.getId())
                        .projectIdentifier(task.getProjectIdentifier())
                        .acceptanceCriteria(task.getAcceptanceCriteria())
                        .name(task.getName())
                        .summary(task.getSummary())
                        .priority(task.getPriority())
                        .startDate(task.getStartDate())
                        .endDate(task.getEndDate())
                        .hours(task.getHours())
                        .status(task.getStatus())
                        .backlog(task.getBacklog())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Double getProjectTotalHours(String id) {
        return taskList.stream()
                .filter(task -> task.getProjectIdentifier().equals(id))
                .filter(task -> !task.getStatus().equals(TaskStatus.DELETED))
                .map(Task::getHours)
                .reduce(Double::sum)
                .orElse(0D);
    }

    @Override
    public Double getHoursByProjectAndTaskStatus(String id, TaskStatus status) {
        return taskList.stream()
                .filter(task -> task.getProjectIdentifier().equals(id))
                .filter(task -> task.getStatus().equals(status))
                .map(Task::getHours)
                .reduce(Double::sum)
                .orElse(0D);
    }

    @Override
    public TaskDto deleteTask(Long taskId) {
        Long foundTaskId = validateTaskExistence(taskId);
        if (foundTaskId == 0) {
            return TaskDto.builder().build();
        } else {
            return taskList.stream()
                    .filter(task -> task.getId().equals(taskId))
                    .findFirst()
                    .map(task -> TaskDto.builder()
                            .id(task.getId())
                            .projectIdentifier(task.getProjectIdentifier())
                            .acceptanceCriteria(task.getAcceptanceCriteria())
                            .name(task.getName())
                            .summary(task.getSummary())
                            .priority(task.getPriority())
                            .startDate(task.getStartDate())
                            .endDate(task.getEndDate())
                            .hours(task.getHours())
                            .status(task.getStatus())
                            .backlog(task.getBacklog())
                            .build())
                    .orElse(TaskDto.builder().build());
        }
    }

    private String validateProjectExistence(List<Project> projectsFound, String id) {
        return projectsFound.stream()
                .map(Project::getProjectIdentifier)
                .filter(p -> p.contains(id))
                .findFirst()
                .orElse("");
    }

    private Long validateTaskExistence(Long id) {
        return taskList.stream()
                .map(Task::getId)
                .filter(p -> p == id)
                .findFirst()
                .orElse(0L);
    }

    public TaskDto createTaskWithOutProject(TaskDto taskDto) {
        Random rd = new Random();
        Long idGenerated = rd.nextLong();
        taskList.add(Task.builder()
                .id(idGenerated)
                .projectIdentifier(taskDto.getProjectIdentifier())
                .acceptanceCriteria(taskDto.getAcceptanceCriteria())
                .name(taskDto.getName())
                .summary(taskDto.getSummary())
                .priority(taskDto.getPriority())
                .startDate(taskDto.getStartDate())
                .endDate(taskDto.getEndDate())
                .hours(taskDto.getHours())
                .status(taskDto.getStatus())
                .backlog(taskDto.getBacklog())
                .build());
        taskDto.setId(idGenerated);
        return taskDto;
    }
}
