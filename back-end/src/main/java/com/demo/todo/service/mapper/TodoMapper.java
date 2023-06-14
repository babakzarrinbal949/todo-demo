package com.demo.todo.service.mapper;

import com.demo.todo.model.Task;
import com.demo.todo.model.Todo;
import com.demo.todo.service.dto.TaskDTO;
import com.demo.todo.service.dto.TodoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    TodoDTO toDto(Todo todo);
    Todo toEntity(TodoDTO todoDTO);
    Task toTaskEntity(TaskDTO taskDTO);
    TaskDTO toTaskDto(Task task);
}
