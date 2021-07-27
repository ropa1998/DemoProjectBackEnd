package com.example.demo.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToDoUpdateDTO {

    private String content;

    private boolean status;

}
