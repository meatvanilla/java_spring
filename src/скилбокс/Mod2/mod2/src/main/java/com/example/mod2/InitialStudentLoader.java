package com.example.mod2;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class InitialStudentLoader {

    @Value("${app.create-default-students:false}")
    private boolean createDefaultStudents;

    @Autowired
    private StudentService studentService;

    @PostConstruct
    public void loadDefaultStudents() {
        if (createDefaultStudents) {
            studentService.addStudent("Иван", "Иванов", 20);
            studentService.addStudent("Мария", "Петрова", 22);
            System.out.println("Созданы студенты по умолчанию.");
        }
    }
}