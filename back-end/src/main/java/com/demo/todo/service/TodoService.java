package com.demo.todo.service;

import com.demo.todo.model.Todo;
import com.demo.todo.repository.TodoRepository;
import com.demo.todo.service.dto.TodoDTO;
import com.demo.todo.service.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    public List<TodoDTO> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream()
                .map(todoMapper::toDto)
                .collect(Collectors.toList());
    }

    public TodoDTO createTodo(TodoDTO todoDTO) {
        Todo todo = todoMapper.toEntity(todoDTO);
        Todo createdTodo = todoRepository.save(todo);
        return todoMapper.toDto(createdTodo);
    }

    public TodoDTO getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found"));
        return todoMapper.toDto(todo);
    }

    public TodoDTO updateTodoById(Long id, TodoDTO updatedTodoDTO) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found"));

        todo.setName(updatedTodoDTO.getName());
        todo.setDescription(updatedTodoDTO.getDescription());
        todo.setTasks(updatedTodoDTO.getTasks().stream()
                .map(todoMapper::toTaskEntity)
                .collect(Collectors.toList()));

        Todo updatedTodo = todoRepository.save(todo);
        return todoMapper.toDto(updatedTodo);
    }

    public void deleteTodoById(Long id) {
        todoRepository.deleteById(id);
    }
}
