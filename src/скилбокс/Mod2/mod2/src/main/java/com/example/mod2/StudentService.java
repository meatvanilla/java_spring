package com.example.mod2;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StudentService {
    private final List<Student> students = new ArrayList<>();

    public Student addStudent(String firstName, String lastName, int age) {
        Student student = new Student(firstName, lastName, age);
        students.add(student);
        return student;
    }

    public boolean deleteStudent(UUID id) {
        return students.removeIf(student -> student.getId().equals(id));
    }

    public String getAllStudents() {
        StringBuilder result = new StringBuilder();
        for (Student student : students) {
            result.append(student.toString()).append("\n"); // Добавляем каждого студента и перенос строки
        }
        return result.toString(); // Возвращаем результат как строку
    }

    public void clearStudents() {
        students.clear();
    }
}
