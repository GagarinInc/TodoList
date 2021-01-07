package my.cv.todolist.services;

import my.cv.todolist.domain.Tag;
import my.cv.todolist.services.interfaces.ITagService;
import my.cv.todolist.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class TagService implements ITagService {
    @PersistenceContext
    EntityManager entityManager;
    private final Converter converter;

    @Autowired
    public TagService(Converter converter) {
        this.converter = converter;
    }

    @Override
    @Transactional
    public Tag findOrCreate(Tag tag) {
        List<Tag> tagList = entityManager.createQuery("SELECT tag FROM Tag tag WHERE tag.name=:name", Tag.class).setParameter("name", tag.getName()).getResultList();
        if (tagList.isEmpty()) {
            entityManager.persist(tag);
            return tag;
        } else {
            return tagList.get(0);
        }
    }
}
