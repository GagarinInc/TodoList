package my.cv.todolist.controllets;

import my.cv.todolist.domain.User;
import my.cv.todolist.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    private final IUserService iUserService;

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping(path = "/create_table_user")
    public ResponseEntity<String> createTableUser() {
        iUserService.createTableUser();
        return new ResponseEntity<>("TABLE User has been created", HttpStatus.OK);
    }

    @PostMapping(path = "/user/create")
    public ResponseEntity<Integer> createUser(@RequestBody User user) {
        return new ResponseEntity<>(iUserService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping(path = "/user/create/{id}")
    public ResponseEntity<User> createTableUser(@PathVariable long id) {
        iUserService.createTableUser();
        return new ResponseEntity<>(iUserService.getUser(id), HttpStatus.OK);
    }

    @GetMapping(path = "/user/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable long  id) {
        return new ResponseEntity<>(iUserService.getUser(id), HttpStatus.OK);
    }

    @PutMapping(path = "/user/update")
    public ResponseEntity<Integer> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(iUserService.updateUser(user), HttpStatus.OK);
    }

    @DeleteMapping(path = "/user/delete/{id}")
    public ResponseEntity<Integer> deleteUser(@PathVariable long id) {
        return new ResponseEntity<>(iUserService.deleteUser(id), HttpStatus.OK);
    }
}
