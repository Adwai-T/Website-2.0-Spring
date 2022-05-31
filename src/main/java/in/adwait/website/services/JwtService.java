package in.adwait.website.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import in.adwait.website.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@PropertySource(value = "config/security.properties")
public class JwtService {
    @Value("${jwt.provider}")
    private String provider;
    @Value("${jwt.secret}")
    private String secret;
    private Algorithm algorithm;

    public String generate(User user) {
        if(algorithm == null) algorithm = Algorithm.HMAC256(secret);

        Map<String, Object> payload = new HashMap<>();
        payload.put("id", user.getId());

        String token = JWT.create()
                .withIssuer(provider)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 36000000))//10hrs
                .withJWTId(generateJwtId(user).toString())
                .withSubject(user.getUsername())
                .withAudience(user.getAuthority())
                .withPayload(payload)
                .sign(algorithm);
        return token;
    }

    public DecodedJWT verify(String jwt) throws JWTVerificationException {
        if(algorithm == null) algorithm = Algorithm.HMAC256(secret);

        JWTVerifier verifier = JWT.require(algorithm).withIssuer(provider).build();
        DecodedJWT decodedJWT = verifier.verify(jwt);
        return decodedJWT;
    }

    private Integer generateJwtId(User user) {
        if(user.getJwtId() != null) {
            return user.getJwtId() + 1;
        }
        return 1;
    }
}
