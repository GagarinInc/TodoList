package my.cv.todolist.controllets;

import my.cv.todolist.domain.PlainObjects.UserPojo;
import my.cv.todolist.domain.User;
import my.cv.todolist.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final IUserService iUserService;

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @PostMapping(path = "/regitration")
    public ResponseEntity<UserPojo> createUser(@RequestBody User user) {
        return new ResponseEntity<>(iUserService.createUser(user), HttpStatus.OK);
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<UserPojo> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(iUserService.getUser(id), HttpStatus.OK);
    }

    @GetMapping(path = "/user/all")
    public ResponseEntity<List<UserPojo>> getAllUser() {
        return new ResponseEntity<>(iUserService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping(path = "/user/{id}")
    public ResponseEntity<UserPojo> updateUser(@RequestBody User user, @PathVariable Long id) {
        return new ResponseEntity<>(iUserService.updateUser(user, id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(iUserService.deleteUser(id), HttpStatus.OK);
    }
}
