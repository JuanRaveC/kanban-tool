package co.com.poli.project.controller;

import co.com.poli.project.dto.ProjectDto;
import co.com.poli.project.gateway.ProjectRequest;
import co.com.poli.project.model.ErrorMessage;
import co.com.poli.project.services.ProjectService;
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
@RequestMapping(value = "/project")
@AllArgsConstructor
public class ProjectController {

    private ProjectService service;

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectRequest request, BindingResult result){
        ProjectDto creationRequest = ProjectDto.builder()
                .projectIdentifier(request.getProjectIdentifier())
                .projectName(request.getProjectName())
                .description(request.getDescription())
                .build();
        ProjectDto created = service.createProject(creationRequest);
        if(created.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects(){
        List<ProjectDto> foundProjects = service.findAllProjects();
        if(foundProjects.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(foundProjects);
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
