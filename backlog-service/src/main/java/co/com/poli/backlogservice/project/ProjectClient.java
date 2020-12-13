package co.com.poli.backlogservice.project;

import co.com.poli.backlogservice.model.Project;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "project-service", fallback = ProjectClientFallback.class)
public interface ProjectClient {

    @GetMapping(value = "/project")
    ResponseEntity<List<Project>> getAllProjects();

}
