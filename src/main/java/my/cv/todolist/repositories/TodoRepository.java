package my.cv.todolist.repositories;

import my.cv.todolist.domain.ToDo;
import my.cv.todolist.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TodoRepository extends CrudRepository<ToDo, Long> {
    Set<ToDo> findAllByUser(User user);
}
