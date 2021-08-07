package com.example.demo;

import com.example.demo.entity.ToDo;
import com.example.demo.repository.ToDoRepository;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class DBStarter {


    private ToDoRepository repository;
    private final Logger logger = LoggerFactory.getLogger(DBStarter.class);

    public DBStarter() { }

    @Autowired
    public DBStarter(ToDoRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        logger.info("Populating database");
        val todo1 = ToDo.builder()
                .content("Frontend training")
                .isDone(true)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
        val todo2 = ToDo.builder()
                .content("Backend training")
                .isDone(true)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
        val todo3 = ToDo.builder()
                .content("Backend demo")
                .isDone(false)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
        val todo4 = ToDo.builder()
                .content("Backend challenge")
                .isDone(false)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
        repository.save(todo1);
        repository.save(todo2);
        repository.save(todo3);
        repository.save(todo4);

        logger.info("Finished populating database");
    }
}
