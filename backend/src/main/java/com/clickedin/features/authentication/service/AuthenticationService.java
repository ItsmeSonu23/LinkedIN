package com.clickedin.features.authentication.service;

import org.springframework.stereotype.Service;

import com.clickedin.features.authentication.dto.AuthenticationRequestBody;
import com.clickedin.features.authentication.dto.AuthenticationResponseBody;
import com.clickedin.features.authentication.model.AuthenticationUser;
import com.clickedin.features.authentication.repositories.AuthenticationUserRepository;
import com.clickedin.features.authentication.utils.Encoder;

@Service
public class AuthenticationService {

    private final Encoder encoder;
    private AuthenticationUserRepository authenticationUserRepository;

    public AuthenticationService(AuthenticationUserRepository authenticationUserRepository, Encoder encoder){
        this.authenticationUserRepository = authenticationUserRepository;
        this.encoder = encoder;
    }

    public AuthenticationUser getUser(String email){
       return authenticationUserRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("User not found"));
    }

    public AuthenticationResponseBody register(AuthenticationRequestBody registerRequestBody){
        authenticationUserRepository.save(new AuthenticationUser(registerRequestBody.getEmail(),encoder.encode(registerRequestBody.getPassword())));
        return new AuthenticationResponseBody("token","User registered Successfully");
    }
    
}
