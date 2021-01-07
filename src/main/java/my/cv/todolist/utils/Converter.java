package my.cv.todolist.utils;

import my.cv.todolist.domain.PlainObjects.TagPojo;
import my.cv.todolist.domain.PlainObjects.ToDoPojo;
import my.cv.todolist.domain.PlainObjects.UserPojo;
import my.cv.todolist.domain.Tag;
import my.cv.todolist.domain.ToDo;
import my.cv.todolist.domain.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Converter {
    public UserPojo userToPojo(User source) {
        UserPojo result = new UserPojo();
        result.setId(source.getId());
        result.setEmail(source.getEmail());
        result.setPassword(source.getPassword());
        result.setToDoTask(toDoToPojo(source.getTodoTask()));
        return result;
    }

    public Set<ToDoPojo> toDoToPojo(Set<ToDo> source) {
        Set<ToDoPojo> result = new HashSet<>();
        for (ToDo toDo : source) {
            ToDoPojo toDoPojo = new ToDoPojo();
            toDoPojo.setId(toDo.getId());
            toDoPojo.setComment(toDo.getComment());
            toDoPojo.setExecuted(toDo.getExecuted());
            toDoPojo.setFinishDate(new Date(toDo.getFinishDate().getTime()));
            toDoPojo.setExecutionDate(toDo.getExecutionDate());
            toDoPojo.setImportant(toDo.getImportant());
            toDoPojo.setInitDate(toDo.getInitDate());
            toDoPojo.setName(toDo.getName());
            toDoPojo.setPriority(toDo.getPriority());
            toDoPojo.setTagSet(tagToPojo(toDo.getTags()));
            toDoPojo.setUserId(toDo.getUser().getId());
            result.add(toDoPojo);
        }
        return result;
    }

    public Set<TagPojo> tagToPojo(Set<Tag> source) {
        Set<TagPojo> result = new HashSet<>();
        for (Tag tag : source) {
            TagPojo tagPojo = new TagPojo();
            tagPojo.setId(tag.getId());
            tagPojo.setName(tag.getName());
            tagPojo.setToDoIds(tag.getToDoSet().stream().map(ToDo::getId).collect(Collectors.toSet()));
            result.add(tagPojo);
        }
        return result;
    }
}
