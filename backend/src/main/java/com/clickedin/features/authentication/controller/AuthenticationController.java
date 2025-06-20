package com.clickedin.features.authentication.controller;

import org.springframework.web.bind.annotation.RestController;

import com.clickedin.features.authentication.dto.AuthenticationRequestBody;
import com.clickedin.features.authentication.dto.AuthenticationResponseBody;
import com.clickedin.features.authentication.model.AuthenticationUser;
import com.clickedin.features.authentication.service.AuthenticationService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import java.io.UnsupportedEncodingException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @GetMapping("/user")
    public AuthenticationUser getUser(@RequestAttribute("authenticatedUser")AuthenticationUser authenticationUser) {
        return authenticationService.getUser(authenticationUser.getEmail());
    }

    public AuthenticationResponseBody loginPage(@Valid @RequestBody AuthenticationRequestBody loginRequestBody){
        return authenticationService.login(loginRequestBody);
    }


    @PostMapping("/register")
    public AuthenticationResponseBody registerPage(@Valid @RequestBody AuthenticationRequestBody registerRequestBody) throws UnsupportedEncodingException, MessagingException {
        return authenticationService.register(registerRequestBody);
    }

    @PutMapping("/validate-email-verification-token")
    public String verifyEmail(@RequestParam String token, @RequestAttribute("authenticatedUser") AuthenticationUser user){
        authenticationService.validateEmailVerificationToken(token,user.getEmail());
        return "Email verified Successfully.";
    }

    @GetMapping("/send-email-verification-token")
    public String sendEmailVerificationToken(@RequestAttribute("authenticatedUser")AuthenticationUser user){
        authenticationService.sendEmailVerificationToken(user.getEmail());
        return "Email verification token sent successfully.";
    }

    @PutMapping("/send-password-reset-token")
    public String sendPasswordResetToken(@RequestParam String email){
        authenticationService.sendPasswordResetToken(email);
        return "Password reset token sent successfully.";
    }

    public String resetPassword(@RequestParam String newPassword, @RequestParam String token, @RequestParam String email){
        authenticationService.resetPassword(email, newPassword, token);
        return "Password reset successfully.";
    }
    
}
