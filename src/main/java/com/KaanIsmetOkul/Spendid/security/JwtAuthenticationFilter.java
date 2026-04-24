package com.KaanIsmetOkul.Spendid.security;

import com.KaanIsmetOkul.Spendid.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        // Skip JWT filter for public endpoints
        return path.equals("/api/v1/login") ||
                path.equals("/api/v1/users") ||
                (path.startsWith("/api/v1/users/") && request.getMethod().equals("POST"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String jwt = null;
        
        try {
            jwt = getJwtFromRequest(request);
            
            if (logger.isDebugEnabled()) {
                logger.debug("=== JWT Filter Debug ===");
                logger.debug("Path: {}", request.getRequestURI());
                logger.debug("JWT Present: {}", (jwt != null));
            }

            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                String username = jwtTokenProvider.getUsernameToken(jwt);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if (logger.isDebugEnabled()) {
                    logger.debug("UserDetails type: {}", userDetails.getClass().getName());
                    logger.debug("Is CustomUserDetails: {}", (userDetails instanceof CustomUserDetails));
                    
                    if (userDetails instanceof CustomUserDetails) {
                        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
                        logger.debug("User ID: {}", customUserDetails.getId());
                    }
                }

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.debug("Authentication set successfully for user: {}", username);
            }
            
        } catch (ExpiredJwtException e) {
            // This is expected for expired tokens - log at INFO level
            logger.info("JWT token expired for request to {}: {}", request.getRequestURI(), e.getMessage());
            // Don't set authentication - Spring Security will return 401
            
        } catch (Exception e) {
            // Unexpected errors - log at ERROR level
            logger.error("JWT authentication failed for request to {}: {}", request.getRequestURI(), e.getMessage(), e);
            // Don't set authentication - Spring Security will return 401
        }

        // Always continue the filter chain
        // If authentication wasn't set, Spring Security's authenticationEntryPoint will handle it
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}