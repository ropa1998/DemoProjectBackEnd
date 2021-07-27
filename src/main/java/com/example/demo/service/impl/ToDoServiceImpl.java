package com.example.demo.service.impl;

import com.example.demo.entity.ToDo;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.ToDoCreateDTO;
import com.example.demo.model.ToDoUpdateDTO;
import com.example.demo.repository.ToDoRepository;
import com.example.demo.service.ToDoService;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Validated
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository toDoRepository;

    public ToDoServiceImpl(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Override
    public List<ToDo> getAllTodos() {
        return toDoRepository.findAll();
    }

    @Override
    public ToDo createTodo(ToDoCreateDTO toDoCreateDTO) {
        ToDo toDo = ToDo.builder()
                .content(toDoCreateDTO.getContent())
                .isDone(false)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
        return toDoRepository.save(toDo);
    }

    @Override
    public ToDo updateToDo(ToDoUpdateDTO toDoUpdateDTO, UUID id) {
        val toModifyToDoOptional = toDoRepository.findById(id);
        if (toModifyToDoOptional.isPresent()) {
            val toModifyToDo = toModifyToDoOptional.get();
            toModifyToDo.setContent(toDoUpdateDTO.getContent());
            toModifyToDo.setDone(toDoUpdateDTO.isStatus());
            toModifyToDo.setLastModifiedDate(OffsetDateTime.now());
            return toDoRepository.save(toModifyToDo);
        }
        throw new EntityNotFoundException(String.format("No to-do found for id: %s", id));
    }

    @Override
    public List<ToDo> getDoneToDos() {
        return toDoRepository.findByIsDoneTrue();
    }

    @Override
    public List<ToDo> getNotDoneToDos() {
        return toDoRepository.findByIsDoneFalse();
    }
}
