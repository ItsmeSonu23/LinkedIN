package com.clickedin.features.authentication.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class AuthenticationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Email
    @Column(unique = true)
    private String email;
    private Boolean emailVerified=false;
    private String emailVerificationToken = null;
    private LocalDateTime emailVerificationTokenExpiryDate = null;
    @JsonIgnore
    private String password;
    private String passwordResetToken=null;
    private LocalDateTime passwordResetTokenExpiryDate = null;

    private String firstName;
    private String lastName;
    private String company;
    private String position;
    private String location;

    private Boolean profileComplete;
    public AuthenticationUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void updateProfileCompleteStatus(){
        this.profileComplete = (firstName != null && lastName != null && company != null && position != null && location != null);
    }
    
}
