package com.clickedin.features.authentication.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class Encoder {
    
    public String encode(String rawString){
       try {
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
         byte[] hash = digest.digest(rawString.getBytes());
         return Base64.getEncoder().encodeToString(hash);
       } catch (NoSuchAlgorithmException e) {
         throw new RuntimeException("Error Encoding string",e);
       }
    }

    public boolean matches(String rawString, String encodedString){
        return encode(rawString).equals(encodedString);
    }
}
