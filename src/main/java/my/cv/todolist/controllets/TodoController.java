package my.cv.todolist.controllets;

import my.cv.todolist.domain.PlainObjects.ToDoPojo;
import my.cv.todolist.domain.ToDo;
import my.cv.todolist.services.interfaces.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class TodoController {
    private final ITodoService iTodoService;

    @Autowired
    public TodoController(ITodoService iTodoService) {
        this.iTodoService = iTodoService;
    }

    @PostMapping(path = "user/{userId}/todo")
    public ResponseEntity<ToDoPojo> createTodo(@RequestBody ToDo toDo, @PathVariable Long userId) {
        return new ResponseEntity<>(iTodoService.createTodo(toDo, userId), HttpStatus.CREATED);
    }

    @PutMapping(path = "user/{userId}/todo/{todoId}")
    public ResponseEntity<String> updateTodo(@RequestBody ToDo toDo, @PathVariable Long userId, @PathVariable Long todoId) {
        return new ResponseEntity<>(iTodoService.updateTodo(toDo, userId), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "user/{userId}/todo/{todoId}")
    public ResponseEntity<String> deleteTodo(@RequestBody ToDo toDo, @PathVariable Long userId, @PathVariable Long todoId) {
        return new ResponseEntity<>(iTodoService.deleteTodo(todoId), HttpStatus.CREATED);
    }

    @GetMapping(path = "user/{userId}/todoes")
    public ResponseEntity<Set<ToDoPojo>> deleteTodo(@PathVariable Long userId) {
        return new ResponseEntity<>(iTodoService.getAllUserTodoes(userId), HttpStatus.CREATED);
    }
}
