package com.victorxavier.product_catalog.infrastructure.security;

import com.victorxavier.product_catalog.domain.service.SecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class SecurityServiceAdapter implements SecurityService {

    private final JwtServiceAdapter jwtService;

    public SecurityServiceAdapter(JwtServiceAdapter jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            try {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();
                    String authHeader = request.getHeader("Authorization");
                    
                    if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        String token = authHeader.substring(7);
                        var claims = jwtService.getClaimsFromToken(token);
                        if (claims != null) {
                            Object userIdClaim = claims.get("userId");
                            if (userIdClaim != null) {
                                return userIdClaim.toString();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error extracting userId from JWT: " + e.getMessage());
            }
            
            return authentication.getName();
        }
        return null;
    }
}