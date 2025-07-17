package com.oscaris.caterers.auth.api;

import com.oscaris.caterers.auth.dtos.LoginUserDTO;
import com.oscaris.caterers.auth.dtos.RegisterUserDTO;
import com.oscaris.caterers.auth.dtos.responses.LoginResponse;
import com.oscaris.caterers.auth.entities.User;
import com.oscaris.caterers.auth.services.AuthenticationService;
import com.oscaris.caterers.auth.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: kev.Ameda
 */

@RestController
@RequestMapping("/auth")
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
}
