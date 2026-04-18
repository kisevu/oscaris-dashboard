package com.oscaris.caterers.auth.services;

import com.oscaris.caterers.auth.dtos.LoginUserDTO;
import com.oscaris.caterers.auth.dtos.RegisterUserDTO;
import com.oscaris.caterers.auth.dtos.responses.RoleResponse;
import com.oscaris.caterers.auth.entities.Role;
import com.oscaris.caterers.auth.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: kev.Ameda
 */

@Service
public interface AuthenticationService {

    public User signUp(RegisterUserDTO registerUserDTO);
    public User authenticate(LoginUserDTO loginUserDTO);
    public RoleResponse addRole();
    public String assignAdminRole(String email);
    public List<Role> getRoles();

    boolean verifyToken(String token);

    void resendVerificationToken(String email);
}
