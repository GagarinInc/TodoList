package my.cv.todolist.services;

import my.cv.todolist.domain.PlainObjects.ToDoPojo;
import my.cv.todolist.domain.Tag;
import my.cv.todolist.domain.ToDo;
import my.cv.todolist.domain.User;
import my.cv.todolist.repositories.TodoRepository;
import my.cv.todolist.repositories.UserRepository;
import my.cv.todolist.services.interfaces.ITagService;
import my.cv.todolist.services.interfaces.ITodoService;
import my.cv.todolist.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TodoService implements ITodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final Converter converter;
    private final ITagService iTagService;

    @Autowired
    public TodoService(TodoRepository todoRepository, UserRepository userRepository, Converter converter, ITagService iTagService) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
        this.converter = converter;
        this.iTagService = iTagService;
    }

    @Override
    @Transactional
    public ToDoPojo createTodo(ToDo toDo, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            toDo.setUser(user.get());
            Set<Tag> tagSet = new HashSet<>(toDo.getTags());
            toDo.getTags().clear();
            tagSet.stream().map(iTagService::findOrCreate).collect(Collectors.toSet()).forEach(toDo::addTag);
            todoRepository.save(toDo);
            return (ToDoPojo) converter.toDoToPojo(new HashSet<ToDo>() {{
                add(toDo);
            }}).toArray()[0];
        } else {
            return new ToDoPojo();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ToDoPojo getTodo(Long todoId) {
        Optional<ToDo> todo = todoRepository.findById(todoId);
        return todo.map(toDo -> (ToDoPojo) converter.toDoToPojo(new HashSet<ToDo>() {{
        }})).orElse(new ToDoPojo());
    }

    @Override
    @Transactional
    public String updateTodo(ToDo toDo, Long todoId) {
        Optional<ToDo> oldTodo = todoRepository.findById(todoId);
        if (oldTodo.isPresent()) {
            todoRepository.save(toDo);
            return "Todo with id='" + toDo.getId() + "' was updated";
        } else {
            return "Todo with id='" + toDo.getId() + "' doesn't exist";
        }
    }

    @Override
    @Transactional
    public String deleteTodo(Long todoId) {
        Optional<ToDo> todo = todoRepository.findById(todoId);
        if (todo.isPresent()) {
            new ArrayList<>(todo.get().getTags()).forEach(tag -> todoRepository.deleteById(tag.getId()));
            todoRepository.deleteById(todoId);
            return "Todo with id='" + todoId + "' was deleted";
        } else {
            return "Todo with id='" + todoId + "' doesn't exist";
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<ToDoPojo> getAllUserTodoes(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(value -> converter.toDoToPojo(todoRepository.findAllByUser(value))).orElse(null);
    }
}
