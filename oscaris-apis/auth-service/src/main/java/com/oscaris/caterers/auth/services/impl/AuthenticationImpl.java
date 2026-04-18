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
import com.oscaris.caterers.auth.exceptions.errors.TokenExpiredException;
import com.oscaris.caterers.auth.exceptions.errors.UserExistsException;
import com.oscaris.caterers.auth.repos.RoleRepository;
import com.oscaris.caterers.auth.repos.UserRepository;
import com.oscaris.caterers.auth.services.AuthenticationService;
import com.oscaris.caterers.auth.services.MailSender;
import com.oscaris.caterers.auth.services.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
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
    private final RoleService roleService;
    private final MailSender mailSender;
    private final VerificationEmailBuilder verificationEmailBuilder;


    @Value("${app.baseUrl:http://localhost:8080}")
    private String baseUrl;



    public AuthenticationImpl(UserRepository userRepository,
                              PasswordEncoder passwordEncoder,
                              AuthenticationManager authenticationManager,
                              RoleRepository roleRepository,
                              RoleService roleService,
                              MailSender mailSender,
                              VerificationEmailBuilder verificationEmailBuilder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.mailSender = mailSender;
        this.verificationEmailBuilder = verificationEmailBuilder;
    }

    @Override
    @Transactional
    public User signUp(RegisterUserDTO registerUserDTO) {
        // Validate uniqueness FIRST (bug: current check is wrong - see below)
        if (userRepository.findByEmail(registerUserDTO.getEmail()).isPresent()) {
            throw new UserExistsException("User with email " + registerUserDTO.getEmail() + " already exists.");
        }

        UserInfo userInfo = UserInfo.builder()
                .firstName(registerUserDTO.getFirstName())
                .lastName(registerUserDTO.getLastName())
                .photoPath(registerUserDTO.getPhotoPath())
                .status(0)
                .build();

        User user = new User(registerUserDTO.getEmail(),
                passwordEncoder.encode(registerUserDTO.getPassword()),
                registerUserDTO.getEmail(), userInfo);
        user.setEnabled(false);

        Optional<Role> roleUser = roleRepository.findByName("USER");
        if (roleUser.isEmpty()) {
            throw new RolesNotRegisteredException("USER role not found. Run addRole() first.");
        }
        user.setRoles(List.of(roleUser.get()));

        User savedUser = userRepository.save(user);
        savedUser.getUserInfo().generateVerificationToken();
        sendVerificationToEmail(savedUser.getEmail(), savedUser.getUserInfo().getVerificationToken());

        return savedUser;
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

    @Override
    public List<Role> getRoles() {
        return roleService.getRoles();
    }

    @Override
    public boolean verifyToken(String token) {
        Optional<User> userOpt = userRepository.findByUserInfoVerificationToken(token);
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        UserInfo info = user.getUserInfo();

        if (info.isTokenExpired()) {
            throw new TokenExpiredException("Your token just expired!!!");
        }

        user.setEnabled(true);
        info.clearVerificationToken();
        info.setStatus(1);
        userRepository.save(user);
        return true;
    }

    @Override
    public void resendVerificationToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailAddressNotFoundException(
                        "No account found with email: " + email));

        // Only allow for unverified users
        if (user.isEnabled()) {
            throw new RuntimeException("Account already verified");
        }

        UserInfo userInfo = user.getUserInfo();

        // Generate NEW token (overwrites old)
        userInfo.generateVerificationToken();

        // Persist updated token
        userRepository.save(user);

        // Send new email
        sendVerificationToEmail(email, userInfo.getVerificationToken());
    }

    private void sendVerificationToEmail(String email, String token){
        String body = verificationEmailBuilder.buildVerificationEmail(email, token);
        mailSender.sendEmail(email,"verify your email - Oscaris Caterers",body);
    }
}
