package com.clickedin.features.authentication.service;

import org.springframework.stereotype.Service;

import com.clickedin.features.authentication.dto.AuthenticationRequestBody;
import com.clickedin.features.authentication.dto.AuthenticationResponseBody;
import com.clickedin.features.authentication.model.AuthenticationUser;
import com.clickedin.features.authentication.repositories.AuthenticationUserRepository;
import com.clickedin.features.authentication.utils.Encoder;
import com.clickedin.features.authentication.utils.JsonWebToken;

@Service
public class AuthenticationService {
    private final JsonWebToken jsonWebToken;
    private final Encoder encoder;
    private AuthenticationUserRepository authenticationUserRepository;

    public AuthenticationService(AuthenticationUserRepository authenticationUserRepository, Encoder encoder,JsonWebToken jsonWebToken){
        this.authenticationUserRepository = authenticationUserRepository;
        this.jsonWebToken = jsonWebToken;
        this.encoder = encoder;
    }

    public AuthenticationUser getUser(String email){
       return authenticationUserRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("User not found"));
    }

    public AuthenticationResponseBody register(AuthenticationRequestBody registerRequestBody){
        authenticationUserRepository.save(new AuthenticationUser(registerRequestBody.getEmail(),encoder.encode(registerRequestBody.getPassword())));
        return new AuthenticationResponseBody("token","User registered Successfully");
    }

    public AuthenticationResponseBody login(AuthenticationRequestBody loginRequestBody){
        AuthenticationUser user = authenticationUserRepository.findByEmail(loginRequestBody.getEmail()).orElseThrow(()-> new IllegalArgumentException("User not found"));
        if(!encoder.matches(loginRequestBody.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Password is incorrect");
        }

        String token = jsonWebToken.generateToken(loginRequestBody.getEmail());
        return new AuthenticationResponseBody(token, "Authentication Succeeded");
    }
    
}
