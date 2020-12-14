package co.com.poli.backlogservice.service;

import co.com.poli.backlogservice.dto.BacklogDto;
import co.com.poli.backlogservice.model.Project;
import co.com.poli.backlogservice.project.ProjectClient;
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

@SpringBootTest(classes = BacklogServiceImpl.class)
public class BacklogServiceImplTest {

    @Mock
    private ProjectClient projectClient;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createBacklogIsNotOk() {
        BacklogServiceImpl service = new BacklogServiceImpl(projectClient);
        BacklogDto backlogDto = BacklogDto.builder()
                .projectIdentifier("Test")
                .build();
        Project project = Project.builder().build();
        List<Project> projectList = new ArrayList<>();
        projectList.add(project);
        when(projectClient.getAllProjects()).thenReturn(ResponseEntity.ok(projectList));

        BacklogDto response = service.createBacklog(backlogDto);

        assertNull(response.getId());
    }

}