package com.example.demo.service;

import com.example.demo.entity.ToDo;
import com.example.demo.model.ToDoCreateDTO;
import com.example.demo.model.ToDoUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface ToDoService {
    List<ToDo> getAllTodos();

    ToDo createTodo(ToDoCreateDTO toDoCreateDTO);

    ToDo updateToDo(ToDoUpdateDTO toDoUpdateDTO, UUID id);

    List<ToDo> getDoneToDos();

    List<ToDo> getNotDoneToDos();

    void deleteToDo(UUID uuid);
}
