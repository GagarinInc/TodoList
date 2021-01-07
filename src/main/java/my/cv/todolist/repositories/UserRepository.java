package my.cv.todolist.repositories;

import my.cv.todolist.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
