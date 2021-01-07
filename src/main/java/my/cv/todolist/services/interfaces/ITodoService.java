package my.cv.todolist.services.interfaces;

import my.cv.todolist.domain.PlainObjects.ToDoPojo;
import my.cv.todolist.domain.ToDo;

import java.util.Set;

public interface ITodoService {
    ToDoPojo createTodo(ToDo toDo, Long userId);

    ToDoPojo getTodo(Long todoId);

    String updateTodo(ToDo toDo, Long todoId);

    String deleteTodo(Long todoId);

    Set<ToDoPojo> getAllUserTodoes(Long userId);
}
