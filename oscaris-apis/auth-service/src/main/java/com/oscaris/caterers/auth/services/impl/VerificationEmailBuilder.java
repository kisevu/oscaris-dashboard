package com.oscaris.caterers.auth.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author: kev.Ameda
 */
@Component
public class VerificationEmailBuilder {

    @Value("${app.baseUrl:http://localhost:8080}")
    private String baseUrl;


    public String buildVerificationEmail(String email, String token) {
        String verifyUrl = baseUrl + "/auth/verify?token=" + token;
        String linkText = "Verify Your Email";  // ← ADD THIS
        return """
        <html>
          <body>
            <p>Hello %s,</p>
            <p>Thank you for registering with Oscaris Caterers.</p>
            <p>Please click the link below to verify your email address:</p>
            <p>
              <a href="%s">%s</a>
            </p>
            <p>If you did not request this, please ignore this email.</p>
          </body>
        </html>
        """.formatted(email, verifyUrl, linkText);  // ← 3 ARGS NOW
    }

}
