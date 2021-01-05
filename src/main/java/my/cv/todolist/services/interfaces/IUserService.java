package my.cv.todolist.services.interfaces;

import my.cv.todolist.domain.User;

public interface IUserService {
    //CRUD operations for User
    int createUser(User user);
    User getUser(long  id);
    int updateUser(User user);
    int deleteUser(long id);

    void createTableUser();
}
