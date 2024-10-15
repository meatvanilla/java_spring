package com.example.mod2;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StudentEventListener {

    @EventListener
    public void handleStudentCreatedEvent(StudentCreatedEvent event) {
        System.out.println("Создан студент: " + event.getStudent());
    }

    @EventListener
    public void handleStudentDeletedEvent(StudentDeletedEvent event) {
        System.out.println("Удалён студент с ID: " + event.getStudentId());
    }
}