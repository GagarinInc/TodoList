package my.cv.todolist.services.interfaces;

import my.cv.todolist.domain.Tag;

public interface ITagService {
    Tag findOrCreate (Tag tag);
}
