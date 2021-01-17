package my.cv.todolist.controllets;

import my.cv.todolist.Exceptions.CustomEmptyDataException;
import my.cv.todolist.annatations.Authenticational;
import my.cv.todolist.domain.PlainObjects.UserPojo;
import my.cv.todolist.domain.User;
import my.cv.todolist.security.TokenManager;
import my.cv.todolist.security.TokenPayload;
import my.cv.todolist.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserController {
    private final IUserService iUserService;
    private final TokenManager tokenManager;
    Long userId;

    @Autowired
    public UserController(IUserService iUserService, TokenManager tokenManager) {
        this.iUserService = iUserService;
        this.tokenManager = tokenManager;
    }

    @PostMapping(path = "/regitration")
    public ResponseEntity<UserPojo> createUser(HttpServletRequest request, @RequestBody User user) {
        return new ResponseEntity<>(iUserService.createUser(user), HttpStatus.OK);
    }

    @PostMapping(path = "/authentication")
    public ResponseEntity<String> authenticateUser(HttpServletRequest request, @RequestBody User user) {
        UserPojo userAuthenticate = iUserService.findUserByEmainAndPassword(user);
        String token = tokenManager.createToken(new TokenPayload(userAuthenticate.getId(), userAuthenticate.getPassword(), new Date()));
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @Authenticational
    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<UserPojo> getUser(HttpServletRequest request, @PathVariable Long userId) {
        return new ResponseEntity<>(iUserService.getUser(userId), HttpStatus.OK);
    }

    @Authenticational
    @GetMapping(path = "/user/all")
    public ResponseEntity<List<UserPojo>> getAllUser(HttpServletRequest request) {
        return new ResponseEntity<>(iUserService.getAllUsers(), HttpStatus.OK);
    }

    @Authenticational
    @PutMapping(path = "/user/{userId}")
    public ResponseEntity<UserPojo> updateUser(HttpServletRequest request, @RequestBody User user, @PathVariable Long userId) {
        return new ResponseEntity<>(iUserService.updateUser(user, userId), HttpStatus.OK);
    }

    @Authenticational
    @DeleteMapping(path = "/user/{userId}")
    public ResponseEntity<String> deleteUser(HttpServletRequest request, @PathVariable Long userId) {
        return new ResponseEntity<>(iUserService.deleteUser(userId), HttpStatus.OK);
    }

    /**
     * Exeption handling
     */
    @ExceptionHandler
    public ResponseEntity<String> onConflictUserEmail(DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ClassUtils.getShortName(exception.getClass()) + ": User with such email already exist");
    }

    @ExceptionHandler
    public ResponseEntity<String> onNonExistingUserId(NoSuchElementException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClassUtils.getShortName(exception.getClass()) + ": User with such id doesn't exist");
    }

    @ExceptionHandler
    public ResponseEntity<String> onNonExistingUsers(EmptyResultDataAccessException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClassUtils.getShortName(exception.getClass()) + " " + exception.getLocalizedMessage() + " " + ": Users doesn't exist yet");
    }

    @ExceptionHandler
    public ResponseEntity<String> sqlProblems(SQLException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ClassUtils.getShortName(exception.getClass()) + " " + exception.getSQLState() + " " + exception.getLocalizedMessage() + " : smth went wrong with sql query");
    }

    @ExceptionHandler
    public ResponseEntity<String> customExceptionHandler(CustomEmptyDataException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClassUtils.getShortName(exception.getClass()) + " " + exception.getLocalizedMessage());
    }
}
