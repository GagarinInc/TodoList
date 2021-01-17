package my.cv.todolist.controllets;

import my.cv.todolist.Exceptions.CustomEmptyDataException;
import my.cv.todolist.annatations.Authenticational;
import my.cv.todolist.domain.PlainObjects.ToDoPojo;
import my.cv.todolist.domain.ToDo;
import my.cv.todolist.services.interfaces.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
public class TodoController {
    private final ITodoService iTodoService;
    Long userId;

    @Autowired
    public TodoController(ITodoService iTodoService) {
        this.iTodoService = iTodoService;
    }

    @Authenticational
    @PostMapping(path = "user/{userId}/todo")
    public ResponseEntity<ToDoPojo> createTodo(HttpServletRequest request, @RequestBody ToDo toDo, @PathVariable Long userId) {
        return new ResponseEntity<>(iTodoService.createTodo(toDo, userId), HttpStatus.CREATED);
    }

    @Authenticational
    @PutMapping(path = "user/{userId}/todo/{todoId}")
    public ResponseEntity<String> updateTodo(HttpServletRequest request, @RequestBody ToDo toDo, @PathVariable Long userId, @PathVariable Long todoId) {
        return new ResponseEntity<>(iTodoService.updateTodo(toDo, todoId, userId), HttpStatus.CREATED);
    }

    @Authenticational
    @DeleteMapping(path = "user/{userId}/todo/{todoId}")
    public ResponseEntity<String> deleteTodo(HttpServletRequest request, @RequestBody ToDo toDo, @PathVariable Long userId, @PathVariable Long todoId) {
        return new ResponseEntity<>(iTodoService.deleteTodo(todoId, userId), HttpStatus.CREATED);
    }

    @Authenticational
    @GetMapping(path = "user/{userId}/todoes")
    public ResponseEntity<Set<ToDoPojo>> deleteTodo(HttpServletRequest request, @PathVariable Long userId) {
        return new ResponseEntity<>(iTodoService.getAllUserTodoes(userId), HttpStatus.CREATED);
    }

    /**
     * Exeption handling
     */
    @ExceptionHandler
    public ResponseEntity<String> onConflictToDoName(DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClassUtils.getShortName(exception.getClass()) + ": ToDo's name is incorrect");
    }

    @ExceptionHandler
    public ResponseEntity<String> onNonExistingToDoId(NoSuchElementException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClassUtils.getShortName(exception.getClass()) + ": ToDo with such id doesn't exist");
    }

    @ExceptionHandler
    public ResponseEntity<String> onNonExistingTodoes(EmptyResultDataAccessException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClassUtils.getShortName(exception.getClass()) + ": Todoes doesn't exist yet");
    }

    @ExceptionHandler
    public ResponseEntity<String> sqlProblems(SQLException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ClassUtils.getShortName(exception.getClass()) + " " + exception.getSQLState() + " " + exception.getLocalizedMessage() + " : smth went wrong with sql query");
    }

    @ExceptionHandler
    public ResponseEntity<String> customExceptionHandler(CustomEmptyDataException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClassUtils.getShortName(exception.getClass()) + " " + exception.getLocalizedMessage());
    }
}
