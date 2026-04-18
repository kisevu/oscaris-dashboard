package com.oscaris.caterers.auth.services.impl;


import com.oscaris.caterers.auth.dtos.LoginUserDTO;
import com.oscaris.caterers.auth.dtos.RegisterUserDTO;
import com.oscaris.caterers.auth.dtos.responses.RoleResponse;
import com.oscaris.caterers.auth.dtos.responses.UserResponse;
import com.oscaris.caterers.auth.entities.Role;
import com.oscaris.caterers.auth.entities.User;
import com.oscaris.caterers.auth.entities.UserInfo;
import com.oscaris.caterers.auth.exceptions.errors.EmailAddressNotFoundException;
import com.oscaris.caterers.auth.exceptions.errors.RolesNotRegisteredException;
import com.oscaris.caterers.auth.exceptions.errors.UserExistsException;
import com.oscaris.caterers.auth.repos.RoleRepository;
import com.oscaris.caterers.auth.repos.UserRepository;
import com.oscaris.caterers.auth.services.AuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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
        if(user.getEmail().equals(userRepository.findByEmail(user.getEmail()))){
            throw new UserExistsException(" user with the provided email exists.");
        }
        Optional<Role> roleUser = roleRepository.findByName("USER");

        /**
         * ROLE_USER IS BY DEFAULT GIVEN TO ANY NEW REGISTRATION.
         *  For advanced rising of role, admin role is assigned to a user through an API
        * */
        roleUser.ifPresent(role -> user.setRoles(List.of(role)));
        if (roleUser.isPresent()){
            return userRepository.save(user);
        }
        throw new RolesNotRegisteredException(" Roles not added by the admin.");
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

    @Transactional
    @Override
    public RoleResponse addRole() {
        Role adminRole = Role.builder()
                .name("ADMIN")
                .isAdmin(true)
                .build();
        Role userRole = Role.builder()
                .name("USER")
                .isAdmin(false)
                .build();

        List<Role> rolesList = roleRepository.findAll();

        if (!rolesList.isEmpty()){
            throw new RolesNotRegisteredException(" Can't add roles, they are present");
        }

        Set<Role> roles = new HashSet<>();
        roles.addAll(List.of(adminRole,userRole));
        roleRepository.saveAll(roles);
        return RoleResponse.builder()
                .message("successfully added roles")
                .build();
    }

    @Override
    public String assignAdminRole(String email) {
        Optional<User> userByEmail = userRepository.findByEmail(email);

        //retrieve the role for the user
        Optional<Role> regularRole = userByEmail
                .get()
                .getRoles()
                .stream()
                .filter(role -> !(role.getIsAdmin()))
                .findFirst();

        if (userByEmail.isPresent()){
            // user is discoverable by given email
            // user is not an admin yet
            User user = userByEmail.orElseThrow();
            List<Role> roles = user.getRoles();
            Role updated  = null;
           for ( Role role: roles ){
               // re-setting the flag
               role.setIsAdmin(true);
               //switch from user -> admin
               role.setName("ROLE_ADMIN");
              updated = roleRepository.save(role);
           }
           roles.add(updated);
           user.setRoles(roles);
           userRepository.save(user);
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
