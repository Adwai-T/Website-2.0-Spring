package in.adwait.website.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    //--might not be unique
    @Column(name = "alias")
    private String alias;
    //--must be unique
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;//Only deserialize password.
    @Column(name = "active")
    private boolean active;
    @Column(name="authority")
    private String authority;
    @Column(name = "jwtId")
    private Integer jwtId;

    public User(String alias, String username, String password, boolean active, String authority) {
        this.alias = alias;
        this.username = username;
        this.password = password;
        this.active = active;
        this.authority = authority;
    }
}
