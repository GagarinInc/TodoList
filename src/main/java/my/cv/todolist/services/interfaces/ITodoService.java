package my.cv.todolist.services.interfaces;

import my.cv.todolist.domain.PlainObjects.ToDoPojo;
import my.cv.todolist.domain.ToDo;

import java.util.List;

public interface ITodoService {
    ToDoPojo createTodo(ToDo toDo, Long userId);

    ToDoPojo getTodo(Long todoId);

    ToDoPojo updateTodo(ToDo toDo, Long todoId);

    ToDoPojo deleteTodo(Long todoId);

    List<ToDoPojo> getAllUserTodoes(Long userId);
}
