package my.cv.todolist.controllets;

import my.cv.todolist.domain.User;
import my.cv.todolist.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    private final IUserService iUserService;

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @PostMapping(path = "/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
