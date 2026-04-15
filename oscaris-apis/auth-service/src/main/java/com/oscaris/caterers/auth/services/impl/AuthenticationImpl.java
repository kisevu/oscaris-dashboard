package com.oscaris.caterers.auth.services.impl;


import com.oscaris.caterers.auth.dtos.LoginUserDTO;
import com.oscaris.caterers.auth.dtos.RegisterUserDTO;
import com.oscaris.caterers.auth.dtos.RoleRequest;
import com.oscaris.caterers.auth.dtos.responses.RoleResponse;
import com.oscaris.caterers.auth.dtos.responses.UserResponse;
import com.oscaris.caterers.auth.entities.Role;
import com.oscaris.caterers.auth.entities.User;
import com.oscaris.caterers.auth.entities.UserInfo;
import com.oscaris.caterers.auth.exceptions.errors.EmailAddressNotFoundException;
import com.oscaris.caterers.auth.repos.RoleRepository;
import com.oscaris.caterers.auth.repos.UserRepository;
import com.oscaris.caterers.auth.services.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * Author: kev.Ameda
 */

@Service
public class AuthenticationImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationImpl(UserRepository userRepository,
                              PasswordEncoder passwordEncoder,
                              AuthenticationManager authenticationManager,
                              RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
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
        Optional<Role> roleUser = roleRepository
                .findByName("ROLE_USER");
        /**
         * ROLE_USER IS BY DEFAULT GIVEN TO ANY NEW REGISTRATION.
         *  For advanced rising of role, admin role is assigned to a user through an API
        * */
        roleUser.ifPresent(role -> user.setRoles(List.of(role)));
        return userRepository.save(user);
    }

    private UserResponse mapToUserResponse(User user){
        return null;
    }

    @Override
    public User authenticate(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByEmail(loginUserDTO.getEmail())
                .orElseThrow(() -> new EmailAddressNotFoundException("User not found"));
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

    @Override
    public RoleResponse addRole(RoleRequest request) {
        String roleName = request.getRolename();
        if ( roleName.contains("admin"))  roleName = "ROLE_ADMIN";
        if ( roleName.contains("user")) roleName = "ROLE_USER";
        Role role = Role.builder()
                .name(roleName)
                .build();
        Optional<Role> byName = roleRepository
                .findByName(role.getName());
        if (!(role.getName().isEmpty()) || !(byName.isPresent())){
            roleRepository.save(role);
            return RoleResponse.builder()
                    .message(" role created successfully")
                    .build();
        } else {
            return RoleResponse.builder()
                    .message(" role potentially exists")
                    .build();
        }

    }

    @Override
    public String assignRole(String email) {
        Optional<User> userByEmail = userRepository.findByEmail(email);
        Optional<Role> regularRole = userByEmail
                .get()
                .getRoles()
                .stream()
                .filter(role -> !(role.getIsAdmin()))
                .findFirst();
        if (userByEmail.isPresent() && regularRole.isPresent()){
            // user is discoverable by given email
            // user is not an admin yet
            User user = userByEmail.orElseThrow();
            List<Role> roles = user.getRoles();
           for ( Role role: roles ){
               role.setIsAdmin(true); // re-setting the flag
               role.setName("ROLE_ADMIN");
               roleRepository.save(role);
           }
            return RoleResponse
                    .builder()
                    .message("Successfully assigned a role of admin.")
                    .build()
                    .getMessage();
        }else {
            return RoleResponse
                    .builder()
                    .message("Admin role could not be assigned.")
                    .build()
                    .getMessage();
        }
    }
}
