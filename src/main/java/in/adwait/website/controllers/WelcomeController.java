package in.adwait.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("home")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.status(HttpStatus.OK).body("Welcome to the website Api service.");
    }

    @GetMapping("admin")
    public ResponseEntity<String> admin() {
        return ResponseEntity.status(HttpStatus.OK).body("Welcome ADMIN");
    }

    @GetMapping("member")
    public ResponseEntity<String> member() {
        return ResponseEntity.status(HttpStatus.OK).body("Welcome MEMBER");
    }

    @GetMapping("user")
    public ResponseEntity<String> user() {
        return ResponseEntity.status(HttpStatus.OK).body("Welcome USER");
    }
}
