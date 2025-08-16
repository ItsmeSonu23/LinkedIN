package com.checked.backend.features.authentication.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.checked.backend.features.authentication.model.AuthenticationUser;
import com.checked.backend.features.authentication.services.AuthenticationService;
import com.checked.backend.features.authentication.utils.JsonWebToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends HttpFilter {
    private final List<String> unsecuredEndPoints = Arrays.asList("/api/v1/auth/register","/api/v1/auth/login","/api/v1/auth/send-password-reset-token","/api/v1/auth/reset-password");

    private final JsonWebToken jsonWebTokenService;
    private final AuthenticationService authenticationService;

    public AuthenticationFilter(JsonWebToken jsonWebTokenService, AuthenticationService authenticationService) {
        this.jsonWebTokenService = jsonWebTokenService;
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(HttpServletRequest request,HttpServletResponse response,FilterChain chain)throws IOException, ServletException{
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        
        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String path = request.getRequestURI();
        if(unsecuredEndPoints.contains(path)){
            chain.doFilter(request, response);
            return;
        }

        try {
            String authorization = request.getHeader("Authorization");
            if(authorization==null || !authorization.startsWith("Bearer ")){
                throw new ServletException("Invalid Token");
            }
            String token = authorization.substring(7);
           
            if(jsonWebTokenService.isTokenExpired(token)){
                throw new ServletException("Token Expired");
            }
            String email = jsonWebTokenService.getEmailFromToken(token);
            AuthenticationUser user = authenticationService.getUser(email);
            request.setAttribute("AuthenticatedUser", user);
            chain.doFilter(request, response);
            
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
        }
    }
}
