package com.checked.backend.features.authentication.services;

import com.checked.backend.features.authentication.dto.AuthenticationRequestBody;
import com.checked.backend.features.authentication.dto.AuthenticationResponseBody;
import com.checked.backend.features.authentication.model.AuthenticationUser;
import com.checked.backend.features.authentication.repository.AuthenticationUserRepo;
import com.checked.backend.features.authentication.utils.Encoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class AuthenticationService {
    private final Encoder encoder;
    private final AuthenticationUserRepo authenticationUserRepo;

    public AuthenticationService(Encoder encoder, AuthenticationUserRepo authenticationUserRepo) {
        this.encoder = encoder;
        this.authenticationUserRepo = authenticationUserRepo;
    }

    public AuthenticationUser getUser(String email){
        return authenticationUserRepo.findByEmail(email).orElseThrow(()->new IllegalArgumentException("user not found!"));
    }

    public AuthenticationResponseBody register(AuthenticationRequestBody registerRequestBody) {
        AuthenticationUser user = new AuthenticationUser(registerRequestBody.getEmail(), encoder.encode(registerRequestBody.getPassword()));
        authenticationUserRepo.save(user);
        return new AuthenticationResponseBody("token","User Registered Successfully!");
    }
}
