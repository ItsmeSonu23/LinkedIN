package com.checked.backend.features.authentication.controller;

import com.checked.backend.features.authentication.dto.AuthenticationRequestBody;
import com.checked.backend.features.authentication.dto.AuthenticationResponseBody;
import com.checked.backend.features.authentication.model.AuthenticationUser;
import com.checked.backend.features.authentication.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/user")
    public AuthenticationUser getUser(){
        return authenticationService.getUser("sonu@example.com");
    }

    @PostMapping("/register")
    public AuthenticationResponseBody register(@Valid @RequestBody AuthenticationRequestBody registerRequestBody){
        return authenticationService.register(registerRequestBody);
    }

    @PostMapping("/login")
    public AuthenticationResponseBody login(@Valid @RequestBody AuthenticationRequestBody loginRequestBody){
        return authenticationService.login(loginRequestBody);
    }
}
