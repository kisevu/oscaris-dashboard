package com.oscaris.caterers.auth.services;

import com.oscaris.caterers.auth.dtos.LoginUserDTO;
import com.oscaris.caterers.auth.dtos.RegisterUserDTO;
import com.oscaris.caterers.auth.entities.User;
import org.springframework.stereotype.Service;

/**
 * Author: kev.Ameda
 */

@Service
public interface AuthenticationService {

    public User signUp(RegisterUserDTO registerUserDTO);
    public User authenticate(LoginUserDTO loginUserDTO);
}
