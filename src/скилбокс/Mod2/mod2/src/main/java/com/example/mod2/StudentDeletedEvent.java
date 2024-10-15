package com.example.mod2;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class StudentDeletedEvent extends ApplicationEvent {
    private final UUID studentId;

    public StudentDeletedEvent(Object source, UUID studentId) {
        super(source);
        this.studentId = studentId;
    }

    public UUID getStudentId() {
        return studentId;
    }
}