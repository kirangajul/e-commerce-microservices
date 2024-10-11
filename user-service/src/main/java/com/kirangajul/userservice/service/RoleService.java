package com.kirangajul.userservice.service;

import java.util.List;
import java.util.Optional;

import com.kirangajul.userservice.model.entity.Role;
import com.kirangajul.userservice.model.entity.RoleName;

public interface RoleService {
    Optional<Role> findByName(RoleName name);
    boolean assignRole(Long id, String roleName);
    boolean revokeRole(Long id, String roleName);
    List<String> getUserRoles(Long id);
}
