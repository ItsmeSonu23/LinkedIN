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
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final AuthenticationUserRepo authenticationUserRepo;
    private final int durationInMinutes = 1;
    private final Encoder encoder;
    private final JsonWebToken jsonWebToken;
    private final EmailService emailService;

    public AuthenticationService(Encoder encoder, AuthenticationUserRepo authenticationUserRepo,
            JsonWebToken jsonWebToken, EmailService emailService) {
        this.encoder = encoder;
        this.authenticationUserRepo = authenticationUserRepo;
        this.jsonWebToken = jsonWebToken;
        this.emailService = emailService;
    }

    public String generateEmailVerificationToken() {
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            token.append(random.nextInt(10));
        }
        return token.toString();
    }

    public void sendEmailVerificationToken(String email) {
        Optional<AuthenticationUser> user = authenticationUserRepo.findByEmail(email);
        if (user.isPresent() && !user.get().getEmailVerified()) {
            String emailVerificationToken = generateEmailVerificationToken();
            String hashedToken = encoder.encode(emailVerificationToken);
            user.get().setEmailVerificationToken(hashedToken);
            user.get().setEmailVerificationTokenExpiryDate(LocalDateTime.now().plusMinutes(durationInMinutes));
            authenticationUserRepo.save(user.get());
            String subject = "Email Verification";
            String body = String.format(
                    "Only one step to take full advantage of CheckedIn.\n\n" + "To Verify your email enter this code : "
                            + "%s\n\n" + "The code will expire in %s minutes",
                    emailVerificationToken, durationInMinutes);
            try {
                emailService.sendEmail(emailVerificationToken, subject, body);
            } catch (Exception e) {
                logger.info("Error while sending email {}", e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Email is already verified!");
        }
    }

    public void validateEmailVerificationToken(String token, String email) {
        Optional<AuthenticationUser> user = authenticationUserRepo.findByEmail(email);
        if (user.isPresent() && encoder.matches(token, user.get().getEmailVerificationToken())
                && !user.get().getEmailVerificationTokenExpiryDate().isBefore(LocalDateTime.now())) {
            user.get().setEmailVerified(true);
            user.get().setEmailVerificationToken(null);
            user.get().setEmailVerificationTokenExpiryDate(null);
            authenticationUserRepo.save(user.get());
        }
        if (user.isPresent() && encoder.matches(token, user.get().getEmailVerificationToken())
                && user.get().getEmailVerificationTokenExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Email verification Token expired!");
        } else {
            throw new IllegalArgumentException("Email verification Token Failed!");
        }
    }

    public AuthenticationUser getUser(String email) {
        return authenticationUserRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("user not found!"));
    }

    public AuthenticationResponseBody register(AuthenticationRequestBody registerRequestBody)
            throws UnsupportedEncodingException, MessagingException {
        AuthenticationUser user = authenticationUserRepo.save(new AuthenticationUser(registerRequestBody.getEmail(),
                encoder.encode(registerRequestBody.getPassword())));

        String emailVerificationToken = generateEmailVerificationToken();
        String hashedToken = encoder.encode(emailVerificationToken);
        user.setEmailVerificationToken(hashedToken);
        user.setEmailVerificationTokenExpiryDate(LocalDateTime.now().plusMinutes(durationInMinutes));
        authenticationUserRepo.save(user);

        String subject = "Email Verification";
        String body = String.format(
                "Only one step to take full advantage of CheckedIn.\n\n" + "To Verify your email enter this code : "
                        + "%s\n\n" + "The code will expire in %s minutes",
                emailVerificationToken, durationInMinutes);
        try {
            emailService.sendEmail(registerRequestBody.getEmail(), subject, body);
        } catch (Exception e) {
            logger.info("Error while sending email {}", e.getMessage());
        }

        String authToken = jsonWebToken.generateToken(registerRequestBody.getEmail());
        return new AuthenticationResponseBody(authToken, "User Registered Successfully!");
    }

    public void sendPasswordResetToken(String email) {
        Optional<AuthenticationUser> user = authenticationUserRepo.findByEmail(email);
        if (user.isPresent()) {
            String resetPasswordToken = generateEmailVerificationToken();
            String hashedToken = encoder.encode(resetPasswordToken);
            user.get().setResetPasswordToken(hashedToken);
            user.get().setResetPasswordTokenExpiryDate(LocalDateTime.now().plusMinutes(durationInMinutes));
            authenticationUserRepo.save(user.get());
            String subject = "Password Reset";
            String body = String.format(
                    "Only one step to take full advantage of CheckedIn.\n\n"
                            + "To Reset your password enter this code : "
                            + "%s\n\n" + "The code will expire in %s minutes",
                    resetPasswordToken, durationInMinutes);
            try {
                emailService.sendEmail(email, subject, body);
            } catch (Exception e) {
                logger.info("Error while sending email {}", e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("User not found!");
        }
    }

    public void resetPassword(String email, String newPassword, String token) {
        Optional<AuthenticationUser> user = authenticationUserRepo.findByEmail(email);
        if (user.isPresent() && encoder.matches(token, user.get().getResetPasswordToken())
                && !user.get().getResetPasswordTokenExpiryDate().isBefore(LocalDateTime.now())) {
            user.get().setPassword(encoder.encode(newPassword));
            user.get().setResetPasswordToken(null);
            user.get().setResetPasswordTokenExpiryDate(null);
            authenticationUserRepo.save(user.get());
        } else if (user.isPresent() && encoder.matches(token, user.get().getResetPasswordToken())
                && user.get().getResetPasswordTokenExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Password Reset Token Expired!");
        } else {
            throw new IllegalArgumentException("Password Reset Token Failed!");
        }
    }

    public AuthenticationResponseBody login(AuthenticationRequestBody loginRequestBody)
            throws UnsupportedEncodingException, MessagingException {
        AuthenticationUser user = authenticationUserRepo.findByEmail(loginRequestBody.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
        if (!encoder.matches(loginRequestBody.getPassword(), user.getPassword())) {
            return new AuthenticationResponseBody("token", "Invalid Credentials!");
        }
        String token = jsonWebToken.generateToken(loginRequestBody.getEmail());
        emailService.sendEmail(loginRequestBody.getEmail(), "User Logged In Successfully!",
                "User Logged In Successfully!");
        return new AuthenticationResponseBody(token, "User Logged In Successfully!");
    }
}
