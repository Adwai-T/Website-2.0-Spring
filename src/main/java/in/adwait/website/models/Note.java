package in.adwait.website.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Document(collection = "Notes")
public class Note {
    @Id
    private String id;
    @Field("note")
    private String note;
    @Field("topic")
    private String topic;
    @Field("title")
    private String title;
    @Field("date")
    private Date date;
    @Field("userId")
    private long userId;
}
