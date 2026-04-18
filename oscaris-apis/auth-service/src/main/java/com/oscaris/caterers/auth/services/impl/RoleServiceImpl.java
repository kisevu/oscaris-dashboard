package com.oscaris.caterers.auth.services.impl;

import com.oscaris.caterers.auth.entities.Role;
import com.oscaris.caterers.auth.repos.RoleRepository;
import com.oscaris.caterers.auth.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Author: kev.Ameda
 */

@Service
@Transactional
public class RoleServiceImpl  implements RoleService {

    private static  final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void addRole(Role role) {
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
