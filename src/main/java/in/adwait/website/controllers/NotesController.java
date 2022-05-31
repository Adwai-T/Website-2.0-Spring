package in.adwait.website.controllers;

import in.adwait.website.models.ErrorResponse;
import in.adwait.website.models.Note;
import in.adwait.website.models.SuccessResponse;
import in.adwait.website.models.User;
import in.adwait.website.repositories.NotesRepository;
import in.adwait.website.repositories.UserRepository;
import in.adwait.website.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "notes")
public class NotesController {

    @Autowired
    private NotesRepository notesRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("save")
    public ResponseEntity<SuccessResponse> saveNote(
            @RequestHeader("authorization") String jwtHeader, @RequestBody Note note)
            throws UserNotMemberException
    {
        Integer userId = Integer.getInteger(
                jwtService.verify(jwtHeader).getClaim("id").toString());
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new UserNotMemberException("User Not Found");
        }
        else if(user.get().getAuthority() == "USER") {
            throw new UserNotMemberException("Please become a member to save to cloud.");
        }

        Optional<Note> existingNote = notesRepository.findByTitle(note.getTitle());
        if(existingNote.isEmpty()) {
            note.setUserId(user.get().getId());
            note.setDate(new Date());
            note.setId(null);
            if(note.getTopic() == "" || note.getTitle() == null) {
                note.setTopic("other");
            }
            notesRepository.save(note);
        }
        else {
            existingNote.get().setNote(note.getNote());
            existingNote.get().setTopic(note.getTopic());
            notesRepository.save(existingNote.get());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse(
                        HttpStatus.CREATED.value(),
                        "Note saved successfully.")
                );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Note>> getAllNotesByUserId(
            @PathVariable("userId") Long userId,
            @RequestHeader("authorization") String jwt)
            throws UserNotAuthorizedException
    {
        Long id = Long.getLong(jwtService.verify(jwt).getClaim("id").toString());
        if(userId == id) {
            return ResponseEntity.ok(notesRepository.findByUserId(id));
        }
        else {
            throw new UserNotAuthorizedException(
                    "Not authorized to access notes of account with id : " + userId);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(
            @PathVariable("id") String id,
            @RequestHeader("authorization") String jwt)
        throws UserNotAuthorizedException
    {
        Long userId = Long.getLong(jwtService.verify(jwt).getClaim("id").toString());
        Optional<Note> note = notesRepository.findById(id);

        if(userId == note.get().getUserId()) {
            return ResponseEntity.ok(notesRepository.findById(id).get());
        }
        else {
            throw new UserNotAuthorizedException(
                    "Not authorized to access notes with note id : " + id);
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<SuccessResponse> deleteNote(
            @RequestHeader("authorization") String jwt, @RequestParam("id") String id)
            throws UserNotAuthorizedException
    {

        Optional<Note> note = notesRepository.findById(id);
        Optional<User> user = userRepository.findById(
                Integer.getInteger(jwtService.verify(jwt).getClaim("id").toString()));

        if(note.isPresent() && note.get().getUserId() == user.get().getId()) {
            notesRepository.delete(note.get());
        }
        else {
            throw new UserNotAuthorizedException("Not authorized to delete this note.");
        }

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new SuccessResponse(
                        HttpStatus.ACCEPTED.value(),
                        "Note was deleted successfully"));
    }

    private class UserNotMemberException extends Exception{
        public UserNotMemberException(String message) {
            super(message);
        }
    }

    private class UserNotAuthorizedException extends Exception {
        public UserNotAuthorizedException(String message) {
            super(message);
        }
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<ErrorResponse> userNotAuthorizedExceptionHandler(
            UserNotAuthorizedException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(
                        HttpStatus.FORBIDDEN.value(),
                        exception.getMessage()));
    }

    @ExceptionHandler(UserNotMemberException.class)
    public ResponseEntity<ErrorResponse> userNoteMemberExceptionHandler(
            UserNotMemberException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(
                        HttpStatus.FORBIDDEN.value(),
                        exception.getMessage()));
    }
}
