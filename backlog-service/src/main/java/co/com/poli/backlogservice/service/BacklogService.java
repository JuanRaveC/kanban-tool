package co.com.poli.backlogservice.service;

import co.com.poli.backlogservice.dto.BacklogDto;

public interface BacklogService {
    BacklogDto createBacklog(BacklogDto backlogDto);
}
