package com.demo.todo.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TodoDTO {
    private Long id;
    private String name;
    private String description;
    private List<TaskDTO> tasks;
}
