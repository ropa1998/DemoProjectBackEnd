package com.example.demo.repository;

import com.example.demo.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ToDoRepository extends JpaRepository<ToDo, UUID> {

    List<ToDo> findByIsDoneTrue();

    List<ToDo> findByIsDoneFalse();
}
