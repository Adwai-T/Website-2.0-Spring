package in.adwait.website.controllers;

import in.adwait.website.interfaces.ServerResponse;
import in.adwait.website.models.FeedbackMessage;
import in.adwait.website.models.server.ErrorResponse;
import in.adwait.website.models.server.SuccessResponse;
import in.adwait.website.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "contact")
public class FeedbackController {

    @Autowired
    private FeedbackRepository repository;

    @PostMapping(value="send")
    public ResponseEntity<ServerResponse> saveMessage(
            @RequestBody FeedbackMessage feedback) {

        try{
            repository.save(feedback);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(
                            500,
                            "There was error saving feedback. Sorry, for the inconvenience, please try again."
                    ));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(
                        200,
                        "Thank you for your message."));
    }

    @GetMapping(value="getAll")
    public ResponseEntity<Page<FeedbackMessage>> getMessages(
            @RequestParam Integer page, @RequestParam Integer size) {
            try {
                return ResponseEntity.ok(repository.findAll(PageRequest.of(page, size)));
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
    }

    @GetMapping(value = "filter/contacted")
    public ResponseEntity<List<FeedbackMessage>> getAllByContacted(
            @RequestParam Integer page, @RequestParam Integer size, @RequestParam Boolean contacted) {
        try {
            return ResponseEntity
                    .ok(repository.findAllByContacted(
                            contacted, PageRequest.of(page, size)).get());
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @DeleteMapping(value="delete")
    public ResponseEntity<ServerResponse> deleteMessage(
            @RequestParam String id) {
        try {
            repository.deleteById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SuccessResponse(
                            200, "Message Deleted Successfully."));
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
