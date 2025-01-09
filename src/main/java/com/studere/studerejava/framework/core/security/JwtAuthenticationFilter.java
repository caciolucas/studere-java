package com.studere.studerejava.framework.core.security;

import com.studere.studerejava.framework.core.exceptions.NotAuthenticatedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String SECRET_KEY; // 256-bit key

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException, NotAuthenticatedException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // Remove "Bearer "

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String userIdString = claims.getSubject(); // "sub" claim
            if (userIdString == null) {
                throw new NotAuthenticatedException("Invalid JWT: Subject is missing");
            }

            UUID userId = UUID.fromString(userIdString);

            List<String> roles = claims.get("roles", List.class);
            List<GrantedAuthority> authorities = roles != null
                    ? roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                    : List.of(new SimpleGrantedAuthority("ROLE_USER"));

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userId.toString(), null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (ExpiredJwtException e) {
            throw new NotAuthenticatedException("The authentication token is expired. Please login again.");
        } catch (IllegalArgumentException e) {
            throw new NotAuthenticatedException("Invalid authentication token.");
        }

        filterChain.doFilter(request, response);
    }
}
