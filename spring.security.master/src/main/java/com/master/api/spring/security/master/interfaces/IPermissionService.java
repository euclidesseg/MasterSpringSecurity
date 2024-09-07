package com.master.api.spring.security.master.interfaces;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.master.api.spring.security.master.dto.SavePermission;
import com.master.api.spring.security.master.dto.ShowPermission;

public interface IPermissionService {
    public Page<ShowPermission> findAll(Pageable pageable);
    public Optional<ShowPermission> findOneById(Long permissionId);
    public ShowPermission addOnePermission(SavePermission savePermission);
    public boolean removeOnePermission(SavePermission savePermission);
}
