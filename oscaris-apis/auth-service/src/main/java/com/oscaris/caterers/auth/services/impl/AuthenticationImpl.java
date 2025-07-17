package com.oscaris.caterers.auth.services.impl;


import com.oscaris.caterers.auth.dtos.LoginUserDTO;
import com.oscaris.caterers.auth.dtos.RegisterUserDTO;
import com.oscaris.caterers.auth.dtos.responses.UserResponse;
import com.oscaris.caterers.auth.entities.Role;
import com.oscaris.caterers.auth.entities.User;
import com.oscaris.caterers.auth.entities.UserInfo;
import com.oscaris.caterers.auth.repos.UserRepository;
import com.oscaris.caterers.auth.services.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Author: kev.Ameda
 */

@Service
public class AuthenticationImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationImpl(UserRepository userRepository,
                              PasswordEncoder passwordEncoder,
                              AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User signUp(RegisterUserDTO registerUserDTO) {
        RegisterUserDTO req = RegisterUserDTO.builder()
                .email(registerUserDTO.getEmail())
                .password(registerUserDTO.getPassword())
                .firstName(registerUserDTO.getFirstName())
                .lastName(registerUserDTO.getLastName())
                .photoPath(registerUserDTO.getPhotoPath())
                .build();
        UserInfo userInfo = UserInfo.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .photoPath(req.getPhotoPath())
                .status(1)
                .build();
        User user = new User(req.getEmail(),
                passwordEncoder.encode(req.getPassword()),
                req.getEmail(),userInfo);
        user.setEnabled(false);
        Role roleUser = Role.builder()
                .name("ROLE_USER")
                .build();
        user.setRoles(List.of(roleUser));
        return userRepository.save(user);
    }

    private UserResponse mapToUserResponse(User user){
        return null;
    }

    @Override
    public User authenticate(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByEmail(loginUserDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(Objects.nonNull(user)){
            user.setEnabled(true);
            userRepository.save(user);
            if (!user.isEnabled()) {
                throw new RuntimeException("Account not enabled. Please contact Admin for Assistance.");
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUserDTO.getEmail(),
                            loginUserDTO.getPassword()
                    )
            );
        }
        return user;
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
