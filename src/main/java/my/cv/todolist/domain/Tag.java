package my.cv.todolist.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tl_tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tagSet")
    private Set<ToDo> toDoSet = new HashSet<>();

    public Set<ToDo> getToDoSet() {
        return toDoSet;
    }

    public void setTodo(ToDo todo) {
        setTag(todo, false);
    }

    public void setTag(ToDo toDo, boolean isLinked) {
        this.toDoSet.add(toDo);
        if (!isLinked)
            toDo.addTag(this, true);
    }

    public void removeTodo(ToDo todo) {
        removeTodo(todo, false);
    }

    public void removeTodo(ToDo todo, boolean isLinked) {
        this.toDoSet = null;
        if (!isLinked)
            todo.removeTag(this, true);
    }
}
