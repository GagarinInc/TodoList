package my.cv.todolist.controllets;

import my.cv.todolist.domain.PlainObjects.UserPojo;
import my.cv.todolist.domain.User;
import my.cv.todolist.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class UserController {
    private final IUserService iUserService;

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @PostMapping(path = "/user/create")
    public ResponseEntity<UserPojo> createUser(@RequestBody User user) {
        return new ResponseEntity<>(iUserService.createUser(user), HttpStatus.OK);
    }

    @GetMapping(path = "/user/get/{userId}")
    public ResponseEntity<UserPojo> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(iUserService.getUser(userId), HttpStatus.OK);
    }
    @GetMapping(path = "/user/get/all")
    public ResponseEntity<List<UserPojo>> getAllUser() {
        return new ResponseEntity<>(iUserService.getAllUsers(), HttpStatus.OK);
    }
}
