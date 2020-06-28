package com.ae.jpa;

import com.ae.jpa.model.entity.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.ae.jpa.common.constants.KEY_HASH;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Long EXPIRATION_TIME = 3600000L;

    private final AuthenticationManager authenticationManager;

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        UserEntity user = getUserEntity(request);

        logger.info("Username: " + user.getUsername());
        logger.info("Password: " + user.getPassword());

        return authenticationManager
                .authenticate(getAuthenticationToken(user.getUsername(), user.getPassword()));
    }


    UsernamePasswordAuthenticationToken getAuthenticationToken(final String username, final String password) {
        return new UsernamePasswordAuthenticationToken(username.trim(), password);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {

        String token = getAuthenticationToken(authResult);

        response.addHeader("Authorization", "Bearer " + token);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("message", "Login success");

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Authentication Error");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }

    private UserEntity getUserEntity(final HttpServletRequest request) {
        try {
            return new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new UserEntity();
        }
    }

    private Collection<? extends GrantedAuthority> getRoles(final Authentication authResult) {
        return authResult.getAuthorities();
    }

    private Claims getClaims(final Authentication authResult) {
        Claims claims = Jwts.claims();
        try {
            claims .put("authorities", new ObjectMapper().writeValueAsString(getRoles(authResult)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return claims;
    }

    private String getAuthenticationToken(final Authentication authResult) {
        return Jwts.builder()
                .setClaims(getClaims(authResult))
                .setSubject(authResult.getName())
                .signWith(
                        Keys
                        .hmacShaKeyFor(getKeyHash()),
                        SignatureAlgorithm.HS512)
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDate())
                .compact();
    }

    private byte[] getKeyHash() {
        return KEY_HASH.getBytes();
    }

    private Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRATION_TIME);
    }
}
