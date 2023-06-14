package com.demo.todo.service;

import com.demo.todo.model.Todo;
import com.demo.todo.repository.TodoRepository;
import com.demo.todo.service.dto.TodoDTO;
import com.demo.todo.service.mapper.TodoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "spring.config.location=classpath:application-test.properties")
class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;

    @Mock
    private TodoMapper todoMapper;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTodos_ReturnsListOfTodos() {
        // Arrange
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setName("Todo 1");
        todo1.setDescription("Description 1");

        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setName("Todo 2");
        todo2.setDescription("Description 2");

        List<Todo> todos = new ArrayList<>();
        todos.add(todo1);
        todos.add(todo2);

        TodoDTO todoDTO1 = new TodoDTO();
        todoDTO1.setId(1L);
        todoDTO1.setName("Todo 1");
        todoDTO1.setDescription("Description 1");

        TodoDTO todoDTO2 = new TodoDTO();
        todoDTO2.setId(2L);
        todoDTO2.setName("Todo 2");
        todoDTO2.setDescription("Description 2");

        List<TodoDTO> expectedTodoDTOs = new ArrayList<>();
        expectedTodoDTOs.add(todoDTO1);
        expectedTodoDTOs.add(todoDTO2);

        when(todoRepository.findAll()).thenReturn(todos);
        when(todoMapper.toDto(todo1)).thenReturn(todoDTO1);
        when(todoMapper.toDto(todo2)).thenReturn(todoDTO2);

        // Act
        List<TodoDTO> actualTodoDTOs = todoService.getAllTodos();

        // Assert
        assertEquals(expectedTodoDTOs.size(), actualTodoDTOs.size());
        for (int i = 0; i < expectedTodoDTOs.size(); i++) {
            assertEquals(expectedTodoDTOs.get(i), actualTodoDTOs.get(i));
        }

        verify(todoRepository, times(1)).findAll();
        verify(todoMapper, times(1)).toDto(todo1);
        verify(todoMapper, times(1)).toDto(todo2);
    }

    @Test
    void createTodo_ReturnsCreatedTodo() {
        // Arrange
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setName("Todo 1");
        todoDTO.setDescription("Description 1");

        Todo todo = new Todo();
        todo.setName("Todo 1");
        todo.setDescription("Description 1");

        Todo createdTodo = new Todo();
        createdTodo.setId(1L);
        createdTodo.setName("Todo 1");
        createdTodo.setDescription("Description 1");

        TodoDTO expectedTodoDTO = new TodoDTO();
        expectedTodoDTO.setId(1L);
        expectedTodoDTO.setName("Todo 1");
        expectedTodoDTO.setDescription("Description 1");

        when(todoMapper.toEntity(todoDTO)).thenReturn(todo);
        when(todoRepository.save(todo)).thenReturn(createdTodo);
        when(todoMapper.toDto(createdTodo)).thenReturn(expectedTodoDTO);

        // Act
        TodoDTO actualTodoDTO = todoService.createTodo(todoDTO);

        // Assert
        assertEquals(expectedTodoDTO, actualTodoDTO);

        verify(todoMapper, times(1)).toEntity(todoDTO);
        verify(todoRepository, times(1)).save(todo);
        verify(todoMapper, times(1)).toDto(createdTodo);
    }

    @Test
    void getTodoById_ExistingId_ReturnsTodo() {
        // Arrange
        Long todoId = 1L;

        Todo todo = new Todo();
        todo.setId(todoId);
        todo.setName("Todo 1");
        todo.setDescription("Description 1");

        TodoDTO expectedTodoDTO = new TodoDTO();
        expectedTodoDTO.setId(todoId);
        expectedTodoDTO.setName("Todo 1");
        expectedTodoDTO.setDescription("Description 1");

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(todo));
        when(todoMapper.toDto(todo)).thenReturn(expectedTodoDTO);

        // Act
        TodoDTO actualTodoDTO = todoService.getTodoById(todoId);

        // Assert
        assertEquals(expectedTodoDTO, actualTodoDTO);

        verify(todoRepository, times(1)).findById(todoId);
        verify(todoMapper, times(1)).toDto(todo);
    }

    @Test
    void updateTodoById_ExistingId_ReturnsUpdatedTodo() {
        // Arrange
        Long todoId = 1L;

        TodoDTO updatedTodoDTO = new TodoDTO();
        updatedTodoDTO.setName("Updated Todo");
        updatedTodoDTO.setDescription("Updated Description");

        Todo todo = new Todo();
        todo.setId(todoId);
        todo.setName("Todo 1");
        todo.setDescription("Description 1");

        Todo updatedTodo = new Todo();
        updatedTodo.setId(todoId);
        updatedTodo.setName("Updated Todo");
        updatedTodo.setDescription("Updated Description");

        TodoDTO expectedTodoDTO = new TodoDTO();
        expectedTodoDTO.setId(todoId);
        expectedTodoDTO.setName("Updated Todo");
        expectedTodoDTO.setDescription("Updated Description");

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(todo));
        when(todoRepository.save(todo)).thenReturn(updatedTodo);
        when(todoMapper.toDto(updatedTodo)).thenReturn(expectedTodoDTO);

        // Act
        TodoDTO actualTodoDTO = todoService.updateTodoById(todoId, updatedTodoDTO);

        // Assert
        assertEquals(expectedTodoDTO, actualTodoDTO);

        verify(todoRepository, times(1)).findById(todoId);
        verify(todoRepository, times(1)).save(todo);
        verify(todoMapper, times(1)).toDto(updatedTodo);
    }

    @Test
    void deleteTodoById_ExistingId_DeletesTodo() {
        // Arrange
        Long todoId = 1L;

        // Act
        todoService.deleteTodoById(todoId);

        // Assert
        verify(todoRepository, times(1)).deleteById(todoId);
    }
}
