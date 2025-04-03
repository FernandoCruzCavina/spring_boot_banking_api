package org.example.bankup.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.example.bankup.entity.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.security.secret}")
    private String secret;

    public JwtUtils() {
    }

    public String generateToken(Customer customer) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("token-service")
                    .withSubject(customer.getMail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                    .sign(algorithm);

        } catch (JWTCreationException ex){
            throw new RuntimeException("JWT creation failed");
        } catch (Exception ex){
            throw new RuntimeException("JWT generation failed");
        }

    }
    public String verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("token-service")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException ex){
            return null;
        }
    }
}
