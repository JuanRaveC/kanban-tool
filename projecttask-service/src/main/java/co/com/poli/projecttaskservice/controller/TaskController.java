package co.com.poli.projecttaskservice.controller;

import co.com.poli.projecttaskservice.dto.TaskDto;
import co.com.poli.projecttaskservice.gateway.TaskRequest;
import co.com.poli.projecttaskservice.model.ErrorMessage;
import co.com.poli.projecttaskservice.model.TaskStatus;
import co.com.poli.projecttaskservice.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/task")
@AllArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping
    public ResponseEntity<TaskDto> createProject(@RequestBody TaskRequest request, BindingResult result){
        TaskDto creationRequest = TaskDto.builder()
                .projectIdentifier(request.getProjectIdentifier())
                .acceptanceCriteria(request.getAcceptanceCriteria())
                .name(request.getName())
                .summary(request.getSummary())
                .priority(request.getPriority())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .hours(request.getHours())
                .status(request.getStatus())
                .backlog(request.getBacklog())
                .build();
        TaskDto created = service.createTask(creationRequest);
        if(created.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<TaskDto>> getAllTaskByProject(@PathVariable("id") String id){
        List<TaskDto> foundTasks = service.getAllTaskByProject(id);
        if(foundTasks.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(foundTasks);
        }
    }

    @GetMapping("/hours/project/{id}")
    public ResponseEntity<Double> getProjectTotalHours(@PathVariable("id") String id){
        Double foundTasks = service.getProjectTotalHours(id);
        if(foundTasks == 0){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(foundTasks);
        }
    }

    @GetMapping("/hours/project/{id}/{status}")
    public ResponseEntity<Double> getHoursByProject(@PathVariable("id") String id,
                                                     @PathVariable("status") TaskStatus status){
        Double foundTasks = service.getHoursByProjectAndTaskStatus(id, status);
        if(foundTasks == 0){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(foundTasks);
        }
    }

    @DeleteMapping("/{id}/{projectId}")
    public ResponseEntity<TaskDto> deleteTask(@PathVariable("id") Long id,
                                              @PathVariable("projectId") String projectId,
                                              BindingResult result){
        TaskDto taskDtoFound = service.deleteTask(id);
        if(taskDtoFound.getId() != null){
            return ResponseEntity.ok(taskDtoFound);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

    }

    private String formatMessage(BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
