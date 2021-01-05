package my.cv.todolist.services;

import my.cv.todolist.domain.User;
import my.cv.todolist.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTableUser() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS Users");
        jdbcTemplate.execute("CREATE TABLE Users(id bigint, email varchar(30), password varchar(30))");
    }

    @Override
    public int createUser(User user) {
        return jdbcTemplate.update(String.format("INSERT INTO Users values ('%s', '%s', '%s')", user.getId(), user.getEmail(), user.getPassword()));
    }

    @Override
    public User getUser(long  id) {
        return jdbcTemplate.queryForObject("SELECT * FROM public.Users WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public int updateUser(User user) {
        return jdbcTemplate.update("UPDATE public.Users SET email='" + user.getEmail() + "', password='" + user.getPassword() + "' WHERE id=" + user.getId());

    }

    @Override
    public int deleteUser(long id) {
        return jdbcTemplate.update("DELETE FROM public.Users WHERE id='" + id + "'");
    }
}
