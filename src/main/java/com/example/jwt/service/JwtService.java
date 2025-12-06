package com.example.jwt.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private static final String ACCESS_SECRET = "ThisIsVeryLongAccessSecretKeyFOrJwt1234567890";
    private static final String REFRESH_SECRET = "ThisIsVeryLongAccessSecretKeyFOrJwt1234567890";

    public String generateAccessToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000*60*15))) //15 minutes
                .signWith(Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000*60*60*24*7))) //7 days
                .signWith(Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

   /* public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }*/



    public String validAccessToken(String token){
       try{
           return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String validRefreshToken(String token){
        /*try{
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }*/
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
