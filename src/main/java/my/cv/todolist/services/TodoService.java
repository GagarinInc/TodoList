package my.cv.todolist.services;

import my.cv.todolist.domain.PlainObjects.ToDoPojo;
import my.cv.todolist.domain.Tag;
import my.cv.todolist.domain.ToDo;
import my.cv.todolist.domain.User;
import my.cv.todolist.services.interfaces.ITagService;
import my.cv.todolist.services.interfaces.ITodoService;
import my.cv.todolist.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TodoService implements ITodoService {
    @PersistenceContext
    EntityManager entityManager;
    private final Converter converter;
    private final ITagService iTagService;

    @Autowired
    public TodoService(Converter converter, ITagService iTagService) {
        this.converter = converter;
        this.iTagService = iTagService;
    }

    @Override
    @Transactional
    public ToDoPojo createTodo(ToDo toDo, Long userId) {
        User user = entityManager.createQuery("SELECT user FROM User user WHERE user.id=:id", User.class).setParameter("id", userId).getSingleResult();
        toDo.setUser(user);
        Set<Tag> tagSet = new HashSet<>(toDo.getTags());
        toDo.getTags().clear();
        tagSet.stream().map(iTagService::findOrCreate).collect(Collectors.toSet()).forEach(toDo::addTag);
        entityManager.persist(toDo);
        return (ToDoPojo) converter.toDoToPojo(new HashSet<ToDo>() {{
            add(toDo);
        }}).toArray()[0];
    }

    @Override
    public ToDoPojo getTodo(Long todoId) {
        return null;
    }

    @Override
    public ToDoPojo updateTodo(ToDo toDo, Long todoId) {
        return null;
    }

    @Override
    public ToDoPojo deleteTodo(Long todoId) {
        return null;
    }

    @Override
    public List<ToDoPojo> getAllUserTodoes(Long userId) {
        return null;
    }
}
