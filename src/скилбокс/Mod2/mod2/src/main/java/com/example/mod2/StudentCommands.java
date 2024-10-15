package com.example.mod2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.UUID;

@ShellComponent
public class StudentCommands {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentEventPublisher eventPublisher;

    @ShellMethod("Добавить нового студента")
    public String addStudent(String firstName, String lastName, int age) {
        Student student = studentService.addStudent(firstName, lastName, age);
        eventPublisher.publishStudentCreatedEvent(student);
        return "Добавлен студент: " + student;
    }

    @ShellMethod("Удалить студента по ID")
    public String deleteStudent(UUID id) {
        if (studentService.deleteStudent(id)) {
            eventPublisher.publishStudentDeletedEvent(id);
            return "Студент удалён.";
        } else {
            return "Студент не найден.";
        }
    }

    @ShellMethod("Показать всех студентов")
    public String listStudents() {
        return studentService.getAllStudents();
    }

    @ShellMethod("Очистить всех студентов")
    public String clearStudents() {
        studentService.clearStudents();
        return "Все студенты удалены.";
    }
}