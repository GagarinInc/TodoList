package my.cv.todolist.controllets;

import my.cv.todolist.domain.PlainObjects.ToDoPojo;
import my.cv.todolist.domain.ToDo;
import my.cv.todolist.services.interfaces.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
