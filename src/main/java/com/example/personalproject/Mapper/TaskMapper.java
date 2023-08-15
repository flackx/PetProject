package com.example.personalproject.Mapper;

import com.example.personalproject.model.Task;
import com.example.personalproject.model.dto.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "status", expression = "java(task.getStatus().name())")
    TaskDTO taskToTaskDTO(Task task);
}
