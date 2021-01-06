package my.cv.todolist.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity()
@Table(name = "tl_todo")

public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "comment")
    private String comment;

    @Column(name = "init_date", nullable = false)
    private Date initDate;

    @Column(name = "finish_date", nullable = false)
    private Date finishDate;

    @Column(name = "execution_date")
    private Date executionDate;

    @Column(name = "important")
    private Boolean important;

    @Column(name = "executed")
    private Boolean executed;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToMany
    @JoinTable(name = "todo_tag_id", joinColumns = @JoinColumn(name = "todo_id"), inverseJoinColumns = @JoinColumn(name = "tl_tag_id"))
    private Set<Tag> tagSet = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user = new User();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        setUser(user, false);
    }

    public void setUser(User user, boolean isLinked) {
        this.user = user;
        if (!isLinked)
            user.addTodo(this, true);
    }

    public void removeUser(User user) {
        removeUser(user, false);
    }

    public void removeUser(User user, boolean isLinked) {
        this.user = null;
        if (!isLinked)
            user.removeTodo(this, true);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public Boolean getImportant() {
        return important;
    }

    public void setImportant(Boolean important) {
        this.important = important;
    }

    public Boolean getExecuted() {
        return executed;
    }

    public void setExecuted(Boolean executed) {
        this.executed = executed;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Set<Tag> getTags() {
        return tagSet;
    }

    public void addTag(Tag tag) {
        addTag(tag, false);
    }

    public void addTag(Tag tag, boolean isLinked) {
        getTags().add(tag);
        if (!isLinked)
            tag.setTag(this, true);
    }

    public void removeTag(Tag tag) {
        removeTag(tag, false);
    }

    public void removeTag(Tag tag, boolean isLinked) {
        getTags().remove(tag);
        if (!isLinked)
            tag.removeTodo(this, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToDo)) return false;
        ToDo toDo = (ToDo) o;
        return id == toDo.id &&
                name.equals(toDo.name) &&
                Objects.equals(comment, toDo.comment) &&
                initDate.equals(toDo.initDate) &&
                Objects.equals(finishDate, toDo.finishDate) &&
                Objects.equals(executionDate, toDo.executionDate) &&
                Objects.equals(important, toDo.important) &&
                Objects.equals(executed, toDo.executed) &&
                priority == toDo.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, comment, initDate, finishDate, executionDate, important, executed, priority);
    }
}
