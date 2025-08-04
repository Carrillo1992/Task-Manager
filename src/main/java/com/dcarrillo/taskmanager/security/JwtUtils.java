package com.dcarrillo.taskmanager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecretString;

    @Value("${jwt.expiration.ms}")
    private int jwtSecretExpirationMs;

    private SecretKey jwtSecretKey;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @PostConstruct
    private void init(){
        byte[] keyBytes = jwtSecretString.getBytes();
        if (keyBytes.length < 32 ){
            logger.error("El token es demasiado corto");
        }
        this.jwtSecretKey = Keys.hmacShaKeyFor((keyBytes));
    }

    public String generateToken(UserDetails userDetails, Long userId){
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> roles= userDetails.getAuthorities();

        if (roles != null && !roles.isEmpty()){
            claims.put("roles", roles.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
        }
        claims.put("userId", userId);
        return createToken(claims, userDetails.getUsername());
    }

    public String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ jwtSecretExpirationMs))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token , Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return extractClaim(token , Claims::getExpiration);
    }

    public<T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaim(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaim(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
}
