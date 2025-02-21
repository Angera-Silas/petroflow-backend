package com.angerasilas.petroflow_backend.security;

import java.io.IOException;
import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.filter.OncePerRequestFilter;

import com.angerasilas.petroflow_backend.service.UserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService; // Required to load user details

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
            throws jakarta.servlet.ServletException, IOException {
        String token = null;

        // Try to get token from Authorization header
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }

        // Try to get token from cookies if missing in the header
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("jwt".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }

        if (token == null || !jwtUtil.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract username and set request attribute
        String username = jwtUtil.extractUsername(token);
        request.setAttribute("username", username);

        // Auto-refresh token if it's close to expiry
        if (jwtUtil.isTokenExpired(token)) {
            String newToken = jwtUtil.generateToken(username);

            Cookie newCookie = new Cookie("jwt", newToken);
            newCookie.setHttpOnly(true);
            newCookie.setPath("/");
            response.addCookie(newCookie);
        }

        filterChain.doFilter(request, response);

        // Load user details
        // userDetailsService.loadUserByUsername(username);

    }
}
