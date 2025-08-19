package com.victorxavier.product_catalog.infrastructure.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServiceAdapter jwtService;

    public JwtAuthenticationFilter(JwtServiceAdapter jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String path = request.getRequestURI();
        String method = request.getMethod();
        System.out.println("=== JWT FILTER: Processing request - " + method + " " + path + " ===");
        
        if (path.startsWith("/api/auth/") ||
            (path.equals("/api/users/register") && method.equals("POST")) ||
            path.startsWith("/h2-console") ||
            path.startsWith("/api/debug")) {
            System.out.println("=== JWT FILTER: Allowing request without authentication for path: " + path + " ===");
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        System.out.println("=== JWT FILTER: Authorization header: " + authHeader + " ===");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println("=== JWT FILTER: Extracted token: " + token.substring(0, Math.min(token.length(), 20)) + "... ===");
            
            try {
                if (jwtService.validateToken(token)) {
                    String username = jwtService.getUsernameFromToken(token);
                    Claims claims = jwtService.getClaimsFromToken(token);
                    
                    if (username != null && claims != null) {
                        System.out.println("=== JWT FILTER: Token valid for user: " + username + " ===");
                        
                        String rolesString = claims.get("roles", String.class);
                        List<SimpleGrantedAuthority> authorities;
                        
                        if (rolesString != null && !rolesString.isEmpty()) {
                            authorities = Arrays.stream(rolesString.split(","))
                                    .map(String::trim)
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());
                        } else {
                            authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
                        }
                        
                        System.out.println("=== JWT FILTER: User authorities: " + authorities + " ===");
                        
                        var authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        
                        System.out.println("=== JWT FILTER: Authentication set for user: " + username + " ===");
                    }
                } else {
                    System.out.println("=== JWT FILTER: Invalid token ===");
                }
            } catch (Exception e) {
                System.err.println("=== JWT FILTER: Error processing token: " + e.getMessage() + " ===");
                e.printStackTrace();
            }
        } else {
            System.out.println("=== JWT FILTER: No valid Authorization header found ===");
        }
        
        System.out.println("=== JWT FILTER: Continuing filter chain ===");
        filterChain.doFilter(request, response);
    }
}