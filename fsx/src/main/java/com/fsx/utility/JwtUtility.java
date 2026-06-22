package com.fsx.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtility { //Create(Encryption) and Validate (Decryption)

    private String SECRET_KEY = "asdgsdag234hlsajbag96544yk4565g6544ter84nddgnerj5345jkl";
    SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)); //this line takes the BASE64 string and decodes it into raw bytes then converts it into a valid cryptographic key suitable for HMAC-SHA algorithms

    /*
     * This class does following things
     * 1. It creates the token for us.
     * 2. It validates the token using Claims class
     * 3. it also extracts the username from the token
     * 4. Set the expiration of Token
     * */

    /* Creating the token */
    public String generateToken(String username){
        Map<String , Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1 * 60 * 60 * 24 * 1000))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    /* Validating the token */
    public Boolean validateToken(String token, String username){
        final String extractedUsername = extractUsername(token);
        return(extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims :: getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
