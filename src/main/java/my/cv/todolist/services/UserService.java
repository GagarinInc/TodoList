package my.cv.todolist.services;

import my.cv.todolist.domain.User;
import my.cv.todolist.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Override
    public int createUser(User user) {
        return 0;
    }

    @Override
    public User getUser(long  id) {
        return null;
    }

    @Override
    public int updateUser(User user) {
        return 0;

    }

    @Override
    public int deleteUser(long id) {
        return 0;
    }
}
