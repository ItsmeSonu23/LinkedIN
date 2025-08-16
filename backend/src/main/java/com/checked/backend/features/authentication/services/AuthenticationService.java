package com.checked.backend.features.authentication.services;

import com.checked.backend.features.authentication.dto.AuthenticationRequestBody;
import com.checked.backend.features.authentication.dto.AuthenticationResponseBody;
import com.checked.backend.features.authentication.model.AuthenticationUser;
import com.checked.backend.features.authentication.repository.AuthenticationUserRepo;
import com.checked.backend.features.authentication.utils.Encoder;
import com.checked.backend.features.authentication.utils.JsonWebToken;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final Encoder encoder;
    private final AuthenticationUserRepo authenticationUserRepo;
    private final JsonWebToken jsonWebToken;

    public AuthenticationService(Encoder encoder, AuthenticationUserRepo authenticationUserRepo, JsonWebToken jsonWebToken) {
        this.encoder = encoder;
        this.authenticationUserRepo = authenticationUserRepo;
        this.jsonWebToken = jsonWebToken;
    }

    public AuthenticationUser getUser(String email){
        return authenticationUserRepo.findByEmail(email).orElseThrow(()->new IllegalArgumentException("user not found!"));
    }

    public AuthenticationResponseBody register(AuthenticationRequestBody registerRequestBody) {
        AuthenticationUser user = new AuthenticationUser(registerRequestBody.getEmail(), encoder.encode(registerRequestBody.getPassword()));
        authenticationUserRepo.save(user);
        return new AuthenticationResponseBody("token","User Registered Successfully!");
    }

    public AuthenticationResponseBody login(AuthenticationRequestBody loginRequestBody) {
        AuthenticationUser user = authenticationUserRepo.findByEmail(loginRequestBody.getEmail()).orElseThrow(()->new IllegalArgumentException("User not found!"));
        if(!encoder.matches(loginRequestBody.getPassword(), user.getPassword())){
            return new AuthenticationResponseBody("token","Invalid Credentials!");
        }
        String token = jsonWebToken.generateToken(loginRequestBody.getEmail());
        return new AuthenticationResponseBody(token,"User Logged In Successfully!");
    }
}
