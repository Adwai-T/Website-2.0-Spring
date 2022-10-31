package in.adwait.website.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "Comments")
public class Comment {

    @Id
    private String id;

    private String comment;

    private String topic;

    @Field(value = "userId")
    private Long user;

    private Date date;

    public Comment(String comment, String topic, Long user) {
        this.comment = comment;
        this.topic = topic;
        this.user = user;
        this.date = new Date();
    }
}
