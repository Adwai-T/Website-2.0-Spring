package in.adwait.website.controllers;

import in.adwait.website.models.server.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "contact")
public class FeedbackController {

    @PostMapping(value="send")
    public ResponseEntity<SuccessResponse> saveMessage() {



        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(
                        200,
                        "Thank you for your message."));
    }

}
