// src/main/java/com/eshop/security/JwtUtil.java
package com.eshop.e_shop_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility class for JWT (JSON Web Token) operations.
 * Handles token generation, validation, and extraction of claims.
 */
@Component // Marks this as a Spring component, allowing it to be autowired
public class JwtUtil {

    // Inject secret key from application.properties
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    // Inject token expiration time from application.properties
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration; // in milliseconds

    /**
     * Extracts the username (subject) from a JWT token.
     * 
     * @param token The JWT string.
     * @return The username (email).
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token using a claims resolver
     * function.
     * 
     * @param token          The JWT string.
     * @param claimsResolver Function to resolve the desired claim from Claims.
     * @param <T>            Type of the claim.
     * @return The extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for a given UserDetails object.
     * Automatically adds user roles as a claim.
     * 
     * @param userDetails The UserDetails object containing user information.
     * @return The generated JWT string.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Add roles as a claim to the JWT
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())); // Store roles as a list of strings
        return generateToken(claims, userDetails);
    }

    /**
     * Generates a JWT token with custom extra claims and UserDetails.
     * 
     * @param extraClaims Additional claims to include in the token payload.
     * @param userDetails The UserDetails object for the subject and roles.
     * @return The generated JWT string.
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims) // Set custom and role claims
                .setSubject(userDetails.getUsername()) // Set subject (username/email)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set issued at time
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Set expiration time
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Sign with key and algorithm
                .compact(); // Build and serialize the token
    }

    /**
     * Validates if a JWT token is valid for a given user.
     * Checks username match and token expiration.
     * 
     * @param token       The JWT string.
     * @param userDetails The UserDetails object to validate against.
     * @return True if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the JWT token has expired.
     * 
     * @param token The JWT string.
     * @return True if the token has expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     * 
     * @param token The JWT string.
     * @return The expiration Date.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims (payload) from the JWT token.
     * 
     * @param token The JWT string.
     * @return The Claims object containing all payload data.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder() // Start building a parser
                .setSigningKey(getSignInKey()) // Set the signing key for verification
                .build() // Build the parser
                .parseClaimsJws(token) // Parse the token (JWS means JSON Web Signature)
                .getBody(); // Get the claims (payload)
    }

    /**
     * Decodes the Base64 secret key and converts it into a Key object.
     * 
     * @return The signing Key.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Decode Base64 secret
        return Keys.hmacShaKeyFor(keyBytes); // Create HMAC-SHA key
    }
}