package my.cv.todolist.services;

import my.cv.todolist.domain.PlainObjects.UserPojo;
import my.cv.todolist.domain.User;
import my.cv.todolist.services.interfaces.IUserService;
import my.cv.todolist.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @PersistenceContext
    EntityManager entityManager;

    private final Converter converter;

    @Autowired
    public UserService(Converter converter) {
        this.converter = converter;
    }

    @Override
    @Transactional()
    public UserPojo createUser(User user) {
        entityManager.persist(user);
        converter.userToPojo(user);
        return converter.userToPojo(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserPojo getUser(Long id) {
        User foundUser = entityManager.createQuery("SELECT user FROM User user WHERE user.id=:id", User.class).setParameter("id", id).getSingleResult();
        return converter.userToPojo(foundUser);
    }

    @Override
    public List<UserPojo> getAllUsers() {
        List<User> foundUser = entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
        return foundUser.stream().map(converter::userToPojo).collect(Collectors.toList());
    }

    @Override
    public UserPojo updateUser(User user) {
        return null;
    }

    @Override
    public UserPojo deleteUser(long id) {
        return null;
    }
}
