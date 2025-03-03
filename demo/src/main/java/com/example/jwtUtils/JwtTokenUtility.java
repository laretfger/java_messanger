package com.example.jwtUtils;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenUtility {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration lifetime;


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> userData = new HashMap<>();
        System.out.println("generateToken");
        List<String> roles = userDetails.getAuthorities().stream().map(role -> role.toString()).toList();
        System.out.println("roles List: " + (roles instanceof List));
        System.out.println("roles Set: " + (roles instanceof Set));

        Date now = new Date();
        Date expired = new Date(now.getTime() + lifetime.toMillis()); 
        System.out.println("put");
        userData.put("username", userDetails.getUsername());
        userData.put("roles", roles);
        return Jwts.builder()
                .setClaims(userData)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getUserName(String token) {
        return getDataToken(token).getSubject();
    }

    public String getUserId(String token) {
        return getDataToken(token).getSubject();
    }

    public List<String> getUserRoles(String token) {
        return getDataToken(token).get("roles", List.class);
    }

    public Claims getDataToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}


