package com.example.demo.service.impl;

import com.example.demo.entity.ToDo;
import com.example.demo.model.ToDoCreateDTO;
import com.example.demo.model.ToDoUpdateDTO;
import com.example.demo.service.ToDoService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ToDoServiceImplTest {

    @Autowired
    private ToDoService toDoService;

    @Test
    void happyPathTest() {
        assertTrue(toDoService.getAllTodos().isEmpty());

        ToDoCreateDTO toDoCreateDTO = ToDoCreateDTO.builder()
                .content("My new content todo")
                .build();
        ToDo savedToDo = toDoService.createTodo(toDoCreateDTO);

        val myTodos = toDoService.getAllTodos();
        assertFalse(myTodos.isEmpty());
        assertEquals(1, myTodos.size());

        val myTodo = myTodos.get(0);
        assertEquals(myTodo, savedToDo);

        ToDoUpdateDTO my_updated_content = ToDoUpdateDTO.builder().content("My updated content").status(false).build();
        val savedUpdatedToDo = toDoService.updateToDo(my_updated_content, myTodo.getUuid());

        val myUpdatedTodos = toDoService.getAllTodos();
        assertFalse(myUpdatedTodos.isEmpty());
        assertEquals(1, myUpdatedTodos.size());

        val myUpdatedToDo = myUpdatedTodos.get(0);
        assertEquals(myUpdatedToDo, savedUpdatedToDo);

        toDoService.deleteToDo(myUpdatedToDo.getUuid());
        assertTrue(toDoService.getAllTodos().isEmpty());
    }

}