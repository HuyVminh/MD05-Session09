package com.ra.service;

import com.ra.model.entity.Role;

public interface IRoleService {
    Role findByRoleName(String roleName);
}
