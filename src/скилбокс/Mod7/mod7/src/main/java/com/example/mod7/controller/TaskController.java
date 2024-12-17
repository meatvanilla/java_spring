package com.example.mod7.controller;


import com.example.mod7.model.Task;
import com.example.mod7.repository.UserRepository;
import com.example.mod7.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_USER')")
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;
    @Autowired
    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskService.findAllTasksWithDetails();
    }

    @GetMapping("/{id}")
    public Mono<Task> getTaskById(@PathVariable String id) {
        return taskService.findTaskByIdWithDetails(id);
    }

    @PostMapping
    public Mono<Task> createTask(@RequestBody Task task, Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .flatMap(user -> {
                    task.setAuthorId(user.getId());
                    return taskService.createTask(task);
                })
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")));
    }

    @PutMapping("/{id}")
    public Mono<Task> updateTask(@PathVariable String id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @PostMapping("/{id}/observers/{observerId}")
    public Mono<Task> addObserver(@PathVariable String id, @PathVariable String observerId) {
        return taskService.addObserver(id, observerId);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable String id) {
        return taskService.deleteTask(id);
    }
}
