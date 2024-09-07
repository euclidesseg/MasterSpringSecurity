package com.master.api.spring.security.master.persistance.repository.security;

import org.springframework.stereotype.Repository;

import com.master.api.spring.security.master.persistance.entity.Security.GrantedPermission;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface IPermissionRepository extends JpaRepository<GrantedPermission, Long> {
    Optional<GrantedPermission> findByRoleIdAndOperationId(Long roleId, Long operationId);

}
