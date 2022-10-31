package in.adwait.website.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import in.adwait.website.interfaces.ServerResponse;
import in.adwait.website.models.Comment;
import in.adwait.website.models.User;
import in.adwait.website.models.server.ErrorResponse;
import in.adwait.website.models.server.SuccessResponse;
import in.adwait.website.repositories.CommentRepository;
import in.adwait.website.repositories.UserRepository;
import in.adwait.website.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "comments")
public class CommentController {

    @Autowired
    private CommentRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    @GetMapping(value = "{id}")
    public ResponseEntity<List<Comment>> getCommentsByUserId(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(repository.findByUser(id));
    }

    @GetMapping(value = "{topic}")
    public ResponseEntity<List<Comment>> getCommentsByTopic(
            @PathVariable String topic
    ) {
        return ResponseEntity.ok(repository.findByTopic(topic));
    }

    @PostMapping
    public ResponseEntity<ServerResponse> addComment(
            @RequestHeader("authorization") String jwt,
            @RequestBody Comment comment
    ) {
        try {
            Optional<User> user = userRepository.findById(comment.getUser());
            if (user.isPresent() && checkUserIsAuthorized(jwt, user.get().getId())) {
                comment.setUser(user.get().getId());
            } else {
                comment.setUser(0L);
            }

            comment.setDate(new Date());
            repository.save(comment);
            return ResponseEntity.ok(new SuccessResponse(
                    0, "Comment Saved successfully."
            ));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(
                            0,
                            "Comment could not be saved." + e.getMessage()));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ServerResponse> deleteComment(
            @PathVariable String id,
            @RequestHeader("authorization") String jwtHeader
    ) {
        try {
            Optional<Comment> comment = repository.findById(id);
            if (comment.isPresent() && checkUserIsAuthorized(jwtHeader, comment.get().getUser())) {
                repository.deleteById(id);
                return ResponseEntity.ok(new SuccessResponse(
                        0, "Comment Saved successfully."
                ));
            } else throw new Exception("Could note find Comment.");
        }
        catch (JWTVerificationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(
                            0,
                            "Login Expired, please login Again."));
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(
                            0,
                            "Comment could not be deleted. Internal Server Error."));
        }
    }

    private boolean checkUserIsAuthorized(String jwtHeader, Long userId) throws JWTVerificationException {

        DecodedJWT jwt = jwtService.verify(jwtHeader);

        Long authorizedUserId = Long.parseLong(jwt.getClaim("id").toString());
        String authorizedUserRole = jwt.getClaim("role").toString();

        if (authorizedUserId == userId || authorizedUserRole == "ADMIN") return true;
        else return false;
    }
}
