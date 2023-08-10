package com.example.personalproject.serializer;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.dto.TaskDTO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
public class TaskSerializer extends JsonSerializer<Task> {

    @Override
    public void serialize(Task task, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDueDate(task.getDueDate());
        taskDTO.setStatus(String.valueOf(task.getStatus()));

        gen.writeObject(taskDTO);
    }
}