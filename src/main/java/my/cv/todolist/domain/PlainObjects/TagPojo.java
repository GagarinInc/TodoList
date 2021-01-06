package my.cv.todolist.domain.PlainObjects;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TagPojo {
    private Long id;
    private String name;
    private Set<Long> toDoIds = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getToDoIds() {
        return toDoIds;
    }

    public void setToDoIds(Set<Long> toDoIds) {
        this.toDoIds = toDoIds;
    }
}
