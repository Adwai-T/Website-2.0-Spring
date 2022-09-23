package in.adwait.website.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "FeedbackMessages")
public class FeedbackMessage {

    @Id
    private String id;
    private String name;
    private String email;
    private String message;
    private boolean contacted;
    private String messageSend;
}
