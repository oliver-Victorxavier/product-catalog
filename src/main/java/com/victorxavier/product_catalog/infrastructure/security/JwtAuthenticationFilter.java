package com.victorxavier.product_catalog.infrastructure.security;

import com.victorxavier.product_catalog.domain.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServiceAdapter jwtService;

    public JwtAuthenticationFilter(JwtServiceAdapter jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Pular o filtro JWT para o endpoint de login
        String requestPath = request.getRequestURI();
        if ("/api/auth/login".equals(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            if (jwtService.validateToken(token)) {
                String username = jwtService.getUsernameFromToken(token);
                Claims claims = jwtService.getClaimsFromToken(token);
                
                if (username != null && claims != null) {
                    // Extrair roles do token JWT
                    String rolesString = claims.get("roles", String.class);
                    List<SimpleGrantedAuthority> authorities;
                    
                    if (rolesString != null && !rolesString.isEmpty()) {
                        authorities = Arrays.stream(rolesString.split(","))
                                .map(String::trim)
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());
                    } else {
                        // Fallback para ROLE_USER se não houver roles no token
                        authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
                    }
                    
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                    
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        
        filterChain.doFilter(request, response);
    }
}