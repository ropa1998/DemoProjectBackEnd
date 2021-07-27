package com.example.demo.repository;

import com.example.demo.DemoApplication;
import com.example.demo.entity.ToDo;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWebClient
@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import({DemoApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)

class ToDoRepositoryTest {

    @Autowired
    private ToDoRepository toDoRepository;

    @Test
    void simpleToDoRepositoryTest() {

        assertTrue(toDoRepository.findAll().isEmpty());

        OffsetDateTime created = OffsetDateTime.now();
        OffsetDateTime modified = OffsetDateTime.now();
        String content = "Do this task";
        boolean done = false;

        val nonPersistedToDo = ToDo.builder()
                .content(content)
                .isDone(done)
                .createdDate(created)
                .lastModifiedDate(modified)
                .build();

        assertNull(nonPersistedToDo.getUuid());
        assertEquals(nonPersistedToDo.getContent(), content);
        assertEquals(nonPersistedToDo.getCreatedDate(), created);
        assertEquals(nonPersistedToDo.getLastModifiedDate(), modified);
        assertEquals(nonPersistedToDo.isDone(), done);

        toDoRepository.save(nonPersistedToDo);

        List<ToDo> todos = toDoRepository.findAll();

        assertFalse(todos.isEmpty());
        assertEquals(1, todos.size());

        val persistedToDo = todos.get(0);

        assertNotNull(persistedToDo.getUuid());
        assertEquals(persistedToDo.getContent(), content);
        assertEquals(persistedToDo.getCreatedDate(), created);
        assertEquals(persistedToDo.getLastModifiedDate(), modified);
        assertEquals(persistedToDo.isDone(), done);
    }

}