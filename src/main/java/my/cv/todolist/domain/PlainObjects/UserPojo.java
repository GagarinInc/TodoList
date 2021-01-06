package my.cv.todolist.domain.PlainObjects;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
@Component
public class UserPojo {
    private Long id;
    private String email;
    private String password;
    private Set<ToDoPojo> toDoTask = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ToDoPojo> getToDoTask() {
        return toDoTask;
    }

    public void setToDoTask(Set<ToDoPojo> toDoTask) {
        this.toDoTask = toDoTask;
    }
}
