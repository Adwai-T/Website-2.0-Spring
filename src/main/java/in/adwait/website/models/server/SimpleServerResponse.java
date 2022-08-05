package in.adwait.website.models.server;

import in.adwait.website.interfaces.ServerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleServerResponse implements ServerResponse {

    private int code;
    private String message;

}
