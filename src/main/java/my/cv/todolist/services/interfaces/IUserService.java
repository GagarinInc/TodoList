package my.cv.todolist.services.interfaces;

import my.cv.todolist.domain.PlainObjects.UserPojo;
import my.cv.todolist.domain.User;

import java.util.List;

public interface IUserService {
    //CRUD operations for User
    UserPojo createUser(User user);

    UserPojo getUser(Long id);

    List<UserPojo> getAllUsers();

    UserPojo updateUser(User user, Long id);

    String deleteUser(long id);

    UserPojo findUserByEmainAndPassword(User user);

}
