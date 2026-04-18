package com.oscaris.caterers.auth.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Embeddable
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Column(nullable = false, length = 8)
    @Size(min = 3, max = 8, message = "First name must be between 3 and 8 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only alphabet characters")
    private String firstName;

    @Column(nullable = false, length = 8)
    @Size(min = 3, max = 8, message = "First name must be between 3 and 8 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only alphabet characters")
    private String lastName;

    @Column(nullable = false)
    private int status = 1;

    @Column(nullable = false)
    private String photoPath;

    @Column(name = "verification_token", length = 500)
    private String verificationToken;

    @Column(name = "token_expiration_time")
    private Instant tokenExpirationTime;

    public boolean isTokenExpired() {
        if (verificationToken == null || tokenExpirationTime == null) {
            return true;
        }
        return Instant.now().isAfter(tokenExpirationTime);
    }

    public void generateVerificationToken() {
        this.verificationToken = UUID.randomUUID().toString();
        this.tokenExpirationTime = Instant.now().plus(Duration.ofMinutes(2));
    }

    public void clearVerificationToken() {
        this.verificationToken = null;
        this.tokenExpirationTime = null;
    }

}
