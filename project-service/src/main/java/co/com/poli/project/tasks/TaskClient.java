package co.com.poli.project.tasks;

import co.com.poli.project.model.ProjectTask;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "projecttask-service", fallback = TaskClientFallback.class)
public interface TaskClient {

    @GetMapping(value = "/task/project/{projectIdentifier}")
    ResponseEntity<List<ProjectTask>> getTask(@PathVariable("projectIdentifier") String id);

}
