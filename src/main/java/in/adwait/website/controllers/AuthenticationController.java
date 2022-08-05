package in.adwait.website.controllers;

import in.adwait.website.models.server.ErrorResponse;
import in.adwait.website.models.security.AuthRequest;
import in.adwait.website.models.security.AuthResponse;
import in.adwait.website.services.JwtService;
import in.adwait.website.services.SimpleUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private SimpleUserDetailsService userDetailsService;

    @PostMapping("")
    public ResponseEntity<AuthResponse> auth(
            @RequestBody AuthRequest requestBody)
            throws BadCredentialsException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestBody.getUsername(), requestBody.getPassword()
                )
        );

        String jwt = jwtService.generate(userDetailsService.getUser());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new AuthResponse(requestBody.getUsername(), jwt));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentialsExceptionHandler() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Username or Password incorrect."));
    }
}
