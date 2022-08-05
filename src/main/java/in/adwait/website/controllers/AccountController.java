package in.adwait.website.controllers;

import in.adwait.website.models.server.ErrorResponse;
import in.adwait.website.models.User;
import in.adwait.website.repositories.UserRepository;
import in.adwait.website.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "account")
public class AccountController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    @PostMapping("new")
    public ResponseEntity<User> createNewUser(@RequestBody User user)
    throws UserAlreadyExistException{

        List<User> users = userRepository.findByUsername(user.getUsername());
        if(users.isEmpty()) {
            user.setActive(true);
            user.setAuthority("USER");
            //--- Encrypt Password before saving
            user.setJwtId(0);
            userRepository.save(user);
        }
        else {
            User existing = users.get(0);
            if(existing.isActive()) {
                throw new UserAlreadyExistException("User with username "
                        + user.getUsername() + " already exists.");
            }
            else if(!existing.isActive()) {
                throw new UserAlreadyExistException("User with username"
                        + user.getUsername() + "already exists but is inactive");
            }
        }

        return ResponseEntity.ok(user);
    }

    //--- Welcome Messages
    @GetMapping("admin")
    public ResponseEntity<String> welcomeAdmin(@RequestHeader("authorization") String jwt) {
        User user = userRepository.findByUsername(jwtService.verify(jwt).getSubject()).get(0);
        return ResponseEntity.ok("Welcome Admin -> " + user.getAlias());
    }
    @GetMapping("member")
    public ResponseEntity<String> welcomeMember(@RequestHeader("authorization") String jwt) {
        User user = userRepository.findByUsername(jwtService.verify(jwt).getSubject()).get(0);
        return ResponseEntity.ok("Welcome Member -> " + user.getAlias());
    }
    @GetMapping("user")
    public ResponseEntity<String> welcomeUser(@RequestHeader("authorization") String jwt) {
        User user = userRepository.findByUsername(jwtService.verify(jwt).getSubject()).get(0);
        return ResponseEntity.ok("Welcome User -> " + user.getAlias());
    }

    //--- Exception Handling

    private class UserAlreadyExistException extends Exception {
        UserAlreadyExistException (String message) {
            super(message);
        }
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> userAlreadyExistsException(UserAlreadyExistException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage()));
    }
}
