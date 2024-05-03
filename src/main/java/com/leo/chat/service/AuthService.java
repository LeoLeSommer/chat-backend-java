package com.leo.chat.service;

import org.springframework.stereotype.Service;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

@Service
public class AuthService {
    /*
     * Get the user email by firebase token
     */
    public String getUserEmail(String token) {
        try {
            // Remove "Bearer " from token if it exists
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return decodedToken.getEmail();
        } catch (Exception e) {
            return null;
        }
    }
}
