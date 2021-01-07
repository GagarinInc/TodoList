package my.cv.todolist.services;

import my.cv.todolist.domain.PlainObjects.UserPojo;
import my.cv.todolist.domain.User;
import my.cv.todolist.repositories.UserRepository;
import my.cv.todolist.services.interfaces.IUserService;
import my.cv.todolist.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private final Converter converter;
    private final UserRepository userRepository;

    @Autowired
    public UserService(Converter converter, UserRepository userRepository) {
        this.converter = converter;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional()
    public UserPojo createUser(User user) {
        userRepository.save(user);
        return converter.userToPojo(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserPojo getUser(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isPresent()) {
            return converter.userToPojo(foundUser.get());
        } else {
            return converter.userToPojo(new User());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserPojo> getAllUsers() {
        List<User> foundUser = (List<User>) userRepository.findAll();
        return foundUser.stream().map(converter::userToPojo).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserPojo updateUser(User user, Long id) {
        Optional<User> oldUser = userRepository.findById(id);
        if (oldUser.isPresent()) {
            User newUser = oldUser.get();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            userRepository.save(newUser);
            return converter.userToPojo(newUser);
        } else {
            return converter.userToPojo(new User());
        }
    }

    @Override
    @Transactional
    public String deleteUser(long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return "User with id='" + id + "' was deleted";
        } else {
            return "User whith id='" + id + "' doesn't exist";
        }
    }
}
