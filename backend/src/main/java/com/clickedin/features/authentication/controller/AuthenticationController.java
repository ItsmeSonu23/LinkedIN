package com.clickedin.features.authentication.controller;

import org.springframework.web.bind.annotation.RestController;

import com.clickedin.features.authentication.dto.AuthenticationRequestBody;
import com.clickedin.features.authentication.dto.AuthenticationResponseBody;
import com.clickedin.features.authentication.model.AuthenticationUser;
import com.clickedin.features.authentication.service.AuthenticationService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @GetMapping("/user")
    public AuthenticationUser getUser() {
        return authenticationService.getUser("sonumish3180@gmail.com");
    }


    @PostMapping("/register")
    public AuthenticationResponseBody registerPage(@Valid @RequestBody AuthenticationRequestBody registerRequestBody) {
        return authenticationService.register(registerRequestBody);
    }
    
}
