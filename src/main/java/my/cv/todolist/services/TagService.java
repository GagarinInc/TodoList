package my.cv.todolist.services;

import my.cv.todolist.domain.Tag;
import my.cv.todolist.repositories.TagRepository;
import my.cv.todolist.services.interfaces.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService implements ITagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public Tag findOrCreate(Tag tag) {
        List<Tag> tagList = tagRepository.findByName(tag.getName());
        tagRepository.save(tag);
        if (tagList.isEmpty()) {
            tagRepository.save(tag);
            return tag;
        } else {
            return tagList.get(0);
        }
    }
}
