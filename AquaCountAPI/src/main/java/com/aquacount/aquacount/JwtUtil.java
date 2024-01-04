package com.aquacount.aquacount;

import com.aquacount.aquacount.model.security.UserDetailsImpl;
import com.aquacount.aquacount.repository.CountersRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Component
public class JwtUtil {

    private static final String SECRET_KEY = "yourSecretKeysdbfhjdbdjvgfsdjhsabhdvdfcdfbhdshvcdsbvjdskncjksdncjsdncjsdncjksndcjnsdjcnsjdkncjsdncjsdncjdsncHJBHJBHBHBJNJjnjnjknk"; // Καλό είναι να αποθηκεύετε το μυστικό κλειδί σας με ασφαλή τρόπο
    private CountersRepository countersRepository;
    public  String generateToken(UserDetailsImpl userDetails) {
        final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().setIssuer("spitsi").setSubject("Jwt-Token")
                .claim("username", userDetails.getUsername())
                .claim("authorities", populateAuthorities(userDetails.getAuthorities()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7200000L)
                )
                .signWith(key).compact();
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authorities = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authorities.add(authority.getAuthority());
        }
        return String.join(
                ",", authorities);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }


    public Long getCounterIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.get("counterId").toString());
    }

    public Authentication getAuthentication(String token) {
        UsernamePasswordAuthenticationToken auth = null;
        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String username = String.valueOf(claims.get("username"));
            String authorities = (String) claims.get("authorities");
            auth = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
            //auth.setDetails(request);
        } catch (Exception e) {
            throw e;
        }
        return auth;
    }
}
