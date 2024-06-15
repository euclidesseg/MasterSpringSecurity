package com.master.api.spring.security.master.interfaces;

import java.util.Optional;

import com.master.api.spring.security.master.persistance.entity.Security.Role;

public interface IRoleService {
        public Optional<Role> findDefaultRole();
}
