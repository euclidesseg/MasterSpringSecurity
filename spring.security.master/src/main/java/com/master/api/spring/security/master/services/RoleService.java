package com.master.api.spring.security.master.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.master.api.spring.security.master.interfaces.IRoleService;
import com.master.api.spring.security.master.persistance.entity.Security.Role;
import com.master.api.spring.security.master.persistance.repository.security.IRoleRepository;

@Service
public class RoleService implements IRoleService {

    @Autowired
    IRoleRepository roleRepository;
    @Value("${security.default.role}")// obtengo una propiedad desde mi arcchivo de propiedades
    private String defaultRole;

    @Override
    public Optional<Role> findDefaultRole() {
        return roleRepository.findByName(defaultRole);
    }
    
}
