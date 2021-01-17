package my.cv.todolist.repositories;

import my.cv.todolist.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByEmailAndPassword(String email, String pwd);
}
