package com.epam.esm.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class JwtProvider {

    public static final String AUTHORIZATION = "Authorization";

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.ttl}")
    private Long timeToLive;

    public  String generateToken(String login) {
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.systemDefault());
        Date date = Date.from(currentTime.plusHours(timeToLive).toInstant(ZoneOffset.UTC));
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    public boolean validateToken(String token) {
        boolean result;
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getLoginFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
