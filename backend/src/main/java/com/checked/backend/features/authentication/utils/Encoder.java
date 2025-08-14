package com.checked.backend.features.authentication.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class Encoder {

    public String encode(String rawString) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = digest.digest(rawString.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    public Boolean matches(String rawString,String encodedString) {
        return  encode(rawString).equals(encodedString);
    }
}
