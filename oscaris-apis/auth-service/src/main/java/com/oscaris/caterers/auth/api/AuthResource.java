package com.oscaris.caterers.auth.api;

import com.oscaris.caterers.auth.dtos.LoginUserDTO;
import com.oscaris.caterers.auth.dtos.RegisterUserDTO;
import com.oscaris.caterers.auth.dtos.responses.LoginResponse;
import com.oscaris.caterers.auth.dtos.responses.RoleResponse;
import com.oscaris.caterers.auth.entities.User;
import com.oscaris.caterers.auth.exceptions.errors.EmailAddressNotFoundException;
import com.oscaris.caterers.auth.services.AuthenticationService;
import com.oscaris.caterers.auth.services.JwtService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Author: kev.Ameda
 */

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthResource {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthResource(JwtService jwtService,
                        AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterUserDTO registerUserDto) {
        User registeredUser = authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto){
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    /**
    * administrator functionalities
    * */
    @PostMapping("/add-roles")
    public ResponseEntity<?> addRoles(){
        RoleResponse response = authenticationService.addRole();
        log.info(" message : {}", response.getMessage());
        return ResponseEntity.ok(response.getMessage());
    }



    @PatchMapping("/assign-admin")
    public ResponseEntity<?> assignAdmin(@RequestParam( name = "email", required = true) String email ){
        String message = authenticationService.assignAdminRole(email);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token) {
        boolean verified = authenticationService.verifyToken(token);
        if (verified) {
            return """
                <h2>Email verified!</h2>
                <p>You can now log in to your account.</p>
                """;
        } else {
            return """
                <h2>Invalid or expired token</h2>
                <p>Please request a new verification email.</p>
                """;
        }
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<?> resendVerificationEmail(@RequestParam String email) {
        try {
            authenticationService.resendVerificationToken(email);
            return ResponseEntity.ok(Map.of(
                    "message", "Verification email sent successfully",
                    "email", email
            ));
        } catch (EmailAddressNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
