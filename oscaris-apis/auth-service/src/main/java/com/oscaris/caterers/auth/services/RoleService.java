package com.oscaris.caterers.auth.services;


import com.oscaris.caterers.auth.entities.Role;

import java.util.List;

/**
 * Author: kev.Ameda
 */
public interface RoleService {
    void addRole( Role role );
    List<Role> getRoles();
}
