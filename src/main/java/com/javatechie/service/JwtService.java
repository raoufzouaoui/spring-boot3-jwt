package com.javatechie.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {


    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //Usage: You call this function when you want to extract a particular claim (e.g., "sub" or a custom claim) from the JWT token.
    //String subject = extractClaim("your_jwt_token_here", Claims::getSubject);
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { // It allows you to extract a specific claim from a JWT token by providing a claimsResolver function that defines how to extract the desired claim from the Claims object.
        //Function<Claims, T>, where Claims is the input type and T is the return type.
        //Function<Claims, String> claimsResolver = claims -> claims.getSubject();
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder() // This method is used to configure and build a JWT parser.
                .setSigningKey(getSignKey()) // This line sets the signing key for the JWT parser. The getSignKey() method is called to retrieve the signing key. In JWT, the signing key is used to verify the authenticity of the token.
                .build() // .build(): This line finalizes the configuration of the JWT parser and builds it
                .parseClaimsJws(token) // This method is used to parse(analyse) a JWT from the provided token. It also verifies the signature of the JWT using the signing key set earlier.
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //The generateToken method you provided is a function that generates a JSON Web Token (JWT) for a given userName
    public String generateToken(String userName){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
