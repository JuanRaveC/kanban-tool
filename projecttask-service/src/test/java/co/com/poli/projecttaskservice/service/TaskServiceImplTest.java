package co.com.poli.projecttaskservice.service;

import co.com.poli.projecttaskservice.dto.TaskDto;
import co.com.poli.projecttaskservice.model.Task;
import co.com.poli.projecttaskservice.model.TaskStatus;
import co.com.poli.projecttaskservice.project.ProjectClient;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TaskServiceImpl.class)
public class TaskServiceImplTest {

    @Mock
    private ProjectClient projectClient;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getProjectTotalHours() {
        TaskServiceImpl service = new TaskServiceImpl(projectClient);
        Double expected = 30D;
        TaskDto taskDto = TaskDto.builder()
                .id(68786L)
                .name("Task Test")
                .summary("Summary Test")
                .hours(15D)
                .status(TaskStatus.IN_PROGRESS)
                .projectIdentifier("798789")
                .build();
        TaskDto taskDto2 = TaskDto.builder()
                .id(68786334L)
                .name("Task Test")
                .summary("Summary Test")
                .hours(15D)
                .status(TaskStatus.IN_PROGRESS)
                .projectIdentifier("798789")
                .build();
        service.createTaskWithOutProject(taskDto);
        service.createTaskWithOutProject(taskDto2);

        Double totalHours = service.getProjectTotalHours("798789");

        assertEquals(totalHours, expected);
    }

    @Test
    public void getHoursByProjectAndTaskStatusWithStatusCompleted() {
        TaskServiceImpl service = new TaskServiceImpl(projectClient);
        Double expected = 15D;
        TaskDto taskDto = TaskDto.builder()
                .id(68786L)
                .name("Task Test")
                .summary("Summary Test")
                .hours(15D)
                .status(TaskStatus.IN_PROGRESS)
                .projectIdentifier("798789")
                .build();
        TaskDto taskDto2 = TaskDto.builder()
                .id(68786334L)
                .name("Task Test")
                .summary("Summary Test")
                .hours(15D)
                .status(TaskStatus.COMPLETED)
                .projectIdentifier("798789")
                .build();
        service.createTaskWithOutProject(taskDto);
        service.createTaskWithOutProject(taskDto2);

        Double totalHours = service.getHoursByProjectAndTaskStatus("798789",TaskStatus.COMPLETED);

        assertEquals(totalHours, expected);
    }

    @Test
    public void getHoursByProjectAndTaskStatusWithStatusInProgress() {
        TaskServiceImpl service = new TaskServiceImpl(projectClient);
        Double expected = 20D;
        TaskDto taskDto = TaskDto.builder()
                .id(68786L)
                .name("Task Test")
                .summary("Summary Test")
                .hours(20D)
                .status(TaskStatus.IN_PROGRESS)
                .projectIdentifier("798789")
                .build();
        TaskDto taskDto2 = TaskDto.builder()
                .id(68786334L)
                .name("Task Test")
                .summary("Summary Test")
                .hours(15D)
                .status(TaskStatus.COMPLETED)
                .projectIdentifier("798789")
                .build();
        service.createTaskWithOutProject(taskDto);
        service.createTaskWithOutProject(taskDto2);

        Double totalHours = service.getHoursByProjectAndTaskStatus("798789",TaskStatus.IN_PROGRESS);

        assertEquals(totalHours, expected);
    }
}