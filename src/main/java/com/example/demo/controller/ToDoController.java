package com.example.demo.controller;

import com.example.demo.entity.Format;
import com.example.demo.entity.ToDo;
import com.example.demo.model.ToDoCreateDTO;
import com.example.demo.model.ToDoUpdateDTO;
import com.example.demo.service.ToDoService;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/todo")
public class ToDoController {
    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public ResponseEntity<List<ToDo>> getAllToDos() {
        val todos = toDoService.getAllTodos();
        return ResponseEntity.status(HttpStatus.OK).body(todos);
    }

    @GetMapping("/done/")
    public ResponseEntity<List<ToDo>> getDoneToDos() {
        val todos = toDoService.getDoneToDos();
        return ResponseEntity.status(HttpStatus.OK).body(todos);
    }

    @GetMapping("/not-done/")
    public ResponseEntity<List<ToDo>> getNotDoneToDos() {
        val todos = toDoService.getNotDoneToDos();
        return ResponseEntity.status(HttpStatus.OK).body(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getToDo(@PathVariable UUID id, @RequestParam(name = "fmt", defaultValue = "json") String format) {
        val todo = toDoService.getToDoById(id);
        switch (format.toLowerCase()) {
           case Format.JSON:
               return ResponseEntity.ok(todo);

           case Format.TEXT:
               val todoStr = String.format("To-Do: %s, with id: %s", todo.getContent(), todo.getUuid().toString());
               return ResponseEntity.ok(todoStr);

           default:
               val msg = new StringBuilder("format should any of the following values: ");
               Arrays.stream(Format.formats).forEach(fmt -> msg.append(String.format("%s, ", fmt)));
               val errMsg = msg.substring(0, msg.length() - 2);
               return ResponseEntity.badRequest().body(errMsg);
       }
    }

    @PostMapping
    public ResponseEntity<ToDo> postNewToDo(@RequestBody ToDoCreateDTO toDoCreateDTO) {
        val createdTodo = toDoService.createTodo(toDoCreateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(createdTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateToDo(@RequestBody ToDoUpdateDTO toDoUpdateDTO, @PathVariable UUID id) {
        val updatedToDo = toDoService.updateToDo(toDoUpdateDTO, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedToDo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable UUID id) {
        toDoService.deleteToDo(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(path = "/customers")
    public String customers(Principal principal) {
        return principal.getName();
    }

}