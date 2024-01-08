package com.ra.service;

import com.ra.model.entity.Role;
import com.ra.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceIMPL implements IRoleService{
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }
}
