package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Todo;
import com.example.postgresdemo.repository.TodoRepository;
import com.example.postgresdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{userId}/todos")
    public List<Todo> getTodoByUserId(@PathVariable Long userId){
        return todoRepository.findByUserId(userId);
    }

    @PostMapping("/users/{userId}/todos")
    public  Todo addTodo(@PathVariable Long userId,
                         @Valid @RequestBody Todo todo){
        return userRepository.findById(userId).
                map(user -> {
                    todo.setUser(user);
                    return todoRepository.save(todo);
        }).orElseThrow(()-> new ResourceNotFoundException("User with id "+ userId +" is not found"));
    }


    @PutMapping("/users/{userId}/todos/{todoId}")
    public Todo updateTodo(@PathVariable Long userId,
                               @PathVariable Long todoId,
                               @Valid @RequestBody Todo todoRequest) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User with id "+ userId +" is not found");
        }

        return todoRepository.findById(todoId).
                map(todo  -> {
                    todo.setTitle(todoRequest.getTitle());
                    todo.setDescription(todoRequest.getDescription());
                    return todoRepository.save(todo);
                }).orElseThrow(() -> new ResourceNotFoundException("Todo List with id "+ todoId +" is not found"));
    }

    @DeleteMapping("/users/{userId}/todos/{todoId}")
    public ResponseEntity<Object> deleteTodo(@PathVariable Long userId,
                                             @PathVariable Long todoId){
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User with id "+ userId +" is not found");
        }

        return todoRepository.findById(todoId).
                map(todo -> {
                    todoRepository.delete(todo);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Todo List with id "+ todoId +" is not found"));
    }
}
