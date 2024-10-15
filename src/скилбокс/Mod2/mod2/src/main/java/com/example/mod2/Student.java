package com.example.mod2;



import lombok.Data;

import java.util.UUID;

@Data
public class Student {
    private final UUID id;  // Уникальный идентификатор для каждого студента
    private String firstName;
    private String lastName;
    private int age;

    public Student(String firstName, String lastName, int age) {
        this.id = UUID.randomUUID();  // Автоматическая генерация уникального id
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Имя: %s, Фамилия: %s, Возраст: %d",
                id.toString(), firstName, lastName, age);
    }

    public UUID getId() {
        return id;
    }
}