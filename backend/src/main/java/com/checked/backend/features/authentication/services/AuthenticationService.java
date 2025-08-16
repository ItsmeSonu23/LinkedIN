package com.checked.backend.features.authentication.services;

import com.checked.backend.features.authentication.dto.AuthenticationRequestBody;
import com.checked.backend.features.authentication.dto.AuthenticationResponseBody;
import com.checked.backend.features.authentication.model.AuthenticationUser;
import com.checked.backend.features.authentication.repository.AuthenticationUserRepo;
import com.checked.backend.features.authentication.utils.EmailService;
import com.checked.backend.features.authentication.utils.Encoder;
import com.checked.backend.features.authentication.utils.JsonWebToken;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final Encoder encoder;
    private final AuthenticationUserRepo authenticationUserRepo;
    private final JsonWebToken jsonWebToken;
    private final EmailService emailService;

    public AuthenticationService(Encoder encoder, AuthenticationUserRepo authenticationUserRepo, JsonWebToken jsonWebToken, EmailService emailService) {
        this.encoder = encoder;
        this.authenticationUserRepo = authenticationUserRepo;
        this.jsonWebToken = jsonWebToken;
        this.emailService = emailService;
    }

    public AuthenticationUser getUser(String email){
        return authenticationUserRepo.findByEmail(email).orElseThrow(()->new IllegalArgumentException("user not found!"));
    }

    public AuthenticationResponseBody register(AuthenticationRequestBody registerRequestBody) throws UnsupportedEncodingException, MessagingException {
        AuthenticationUser user = new AuthenticationUser(registerRequestBody.getEmail(), encoder.encode(registerRequestBody.getPassword()));
        authenticationUserRepo.save(user);
        String token = jsonWebToken.generateToken(registerRequestBody.getEmail());
        emailService.sendEmail(registerRequestBody.getEmail(), "User Registered Successfully!", "User Registered Successfully!");
        return new AuthenticationResponseBody(token,"User Registered Successfully!");
    }

    public AuthenticationResponseBody login(AuthenticationRequestBody loginRequestBody) throws UnsupportedEncodingException, MessagingException {
        AuthenticationUser user = authenticationUserRepo.findByEmail(loginRequestBody.getEmail()).orElseThrow(()->new IllegalArgumentException("User not found!"));
        if(!encoder.matches(loginRequestBody.getPassword(), user.getPassword())){
            return new AuthenticationResponseBody("token","Invalid Credentials!");
        }
        String token = jsonWebToken.generateToken(loginRequestBody.getEmail());
        emailService.sendEmail(loginRequestBody.getEmail(), "User Logged In Successfully!", "User Logged In Successfully!");
        return new AuthenticationResponseBody(token,"User Logged In Successfully!");
    }
}
