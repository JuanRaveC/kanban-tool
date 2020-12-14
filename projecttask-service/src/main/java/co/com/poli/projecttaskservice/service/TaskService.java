package co.com.poli.projecttaskservice.service;

import co.com.poli.projecttaskservice.dto.TaskDto;
import co.com.poli.projecttaskservice.model.TaskStatus;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TaskService {

    TaskDto createTask(TaskDto taskDto);
    List<TaskDto> getAllTaskByProject(String id);
    Double getProjectTotalHours(String id);
    Double getHoursByProjectAndTaskStatus(String id, TaskStatus status);
    TaskDto deleteTask(Long taskId);
}
