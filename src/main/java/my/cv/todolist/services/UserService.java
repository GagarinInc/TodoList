package my.cv.todolist.services;

import my.cv.todolist.Exceptions.CustomEmptyDataException;
import my.cv.todolist.domain.PlainObjects.UserPojo;
import my.cv.todolist.domain.User;
import my.cv.todolist.repositories.UserRepository;
import my.cv.todolist.services.interfaces.IUserService;
import my.cv.todolist.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
            throw new CustomEmptyDataException("Unable to get User with id='" + id + "'");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserPojo> getAllUsers() {
        List<User> foundUser = (List<User>) userRepository.findAll();
        if (!foundUser.isEmpty()) {
            return foundUser.stream().map(converter::userToPojo).collect(Collectors.toList());
        } else {
            throw new EmptyResultDataAccessException("at least ", 1);
        }
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
            throw new CustomEmptyDataException("Unable to update User with id='" + id + "'");
        }
    }

    @Override
    @Transactional
    public String deleteUser(long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return "User with id='" + id + "' was deleted";
        } else {
            throw new CustomEmptyDataException("Unable to delete User with id='" + id + "'");
        }
    }

    @Override
    @Transactional
    public UserPojo findUserByEmainAndPassword(User user) {
        Optional<User> userOptional = userRepository.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if (userOptional.isPresent()) {
            return converter.userToPojo(userOptional.get());
        }
        throw new CustomEmptyDataException("Wrong email='" + user.getEmail() + "' or password. Check email and password");
    }
}
