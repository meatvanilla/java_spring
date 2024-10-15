package com.example.mod2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StudentEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishStudentCreatedEvent(Student student) {
        applicationEventPublisher.publishEvent(new StudentCreatedEvent(this, student));
    }

    public void publishStudentDeletedEvent(UUID id) {
        applicationEventPublisher.publishEvent(new StudentDeletedEvent(this, id));
    }
}