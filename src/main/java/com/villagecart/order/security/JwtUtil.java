package com.villagecart.order.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY="M5D7d6Q1s7n9zY2m4R8YhKkVfU1uN3bJQ0xP7cWgL9A=";
    private final Key key= Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    //Extract username from the Tocken

    public String extractUserName(String token){
        return getClaims(token).getSubject();
    }

    //verify if tocken has expired
    public boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }
    //validate token against expected rules
    public boolean validateToken(String token){
        try{
            return !isTokenExpired(token);
        }catch (Exception e){
            return false;
        }
    }
    private Claims getClaims(String token){
        return Jwts.parser().verifyWith((javax.crypto.SecretKey)key)
                .build().parseSignedClaims(token).getPayload();
    }
}
