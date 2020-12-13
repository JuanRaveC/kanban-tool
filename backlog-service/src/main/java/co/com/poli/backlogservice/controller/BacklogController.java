package co.com.poli.backlogservice.controller;

import co.com.poli.backlogservice.dto.BacklogDto;
import co.com.poli.backlogservice.gateway.BacklogRequest;
import co.com.poli.backlogservice.model.ErrorMessage;
import co.com.poli.backlogservice.service.BacklogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/backlog")
@AllArgsConstructor
public class BacklogController {

    private final BacklogService service;

    @PostMapping
    public ResponseEntity<BacklogDto> createBacklog(@RequestBody BacklogRequest request, BindingResult result){
        BacklogDto creationRequest = BacklogDto.builder()
                .projectIdentifier(request.getProjectIdentifier())
                .project(request.getProject() != null ? request.getProject() : null)
                .projectTasks(request.getProjectTasks() != null ? request.getProjectTasks() : null)
                .build();
        BacklogDto created = service.createBacklog(creationRequest);
        if(created.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
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
