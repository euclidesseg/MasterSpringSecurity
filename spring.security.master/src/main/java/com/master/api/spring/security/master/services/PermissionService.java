package com.master.api.spring.security.master.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.master.api.spring.security.master.dto.SavePermission;
import com.master.api.spring.security.master.dto.ShowPermission;
import com.master.api.spring.security.master.exception.ObjectNotFoundException;
import com.master.api.spring.security.master.interfaces.IPermissionService;
import com.master.api.spring.security.master.persistance.entity.Security.GrantedPermission;
import com.master.api.spring.security.master.persistance.entity.Security.Operation;
import com.master.api.spring.security.master.persistance.entity.Security.Role;
import com.master.api.spring.security.master.persistance.repository.security.IOperationRepository;
import com.master.api.spring.security.master.persistance.repository.security.IPermissionRepository;
import com.master.api.spring.security.master.persistance.repository.security.IRoleRepository;

@Service
public class PermissionService implements IPermissionService{

    @Autowired
    public IPermissionRepository permissionRepository;
    @Autowired
    public IRoleRepository roleRepository;
    @Autowired
    IOperationRepository operationRepository;

    @Override
    public Page<ShowPermission> findAll(Pageable pageable) {
        return this.permissionRepository.findAll(pageable).map(this::mapEntityToShowDto);
        //El método map es una operación de transformación que se aplica a cada elemento de la página de resultados Page<GrantedPermission>.
        //this se refiere a la instancia actual de la clase que contiene este código.
        //::mapEntityToShowDto es una referencia al método mapEntityToShowDto.
    }

    private ShowPermission mapEntityToShowDto(GrantedPermission grantedPermission) {
        if(grantedPermission == null) return null;
        ShowPermission showDto = new ShowPermission();
        showDto.setId(grantedPermission.getId());
        showDto.setRole(grantedPermission.getRole().getName());
        showDto.setOperation(grantedPermission.getOperation().getName());
        showDto.setSttp_method(grantedPermission.getOperation().getHttp_method());
        showDto.setModule(grantedPermission.getOperation().getModule().getName());

        System.out.println(showDto.getId());
        System.out.println(showDto.getRole());
        System.out.println(showDto.getSttp_method());
        System.out.println(showDto.getModule());
        return showDto;
    }

    @Override
    public Optional<ShowPermission> findOneById(Long permissionId){
         return this.permissionRepository.findById(permissionId).map(this::mapEntityToShowDto);
    }
 
    @Override
    public ShowPermission addOnePermission(SavePermission savePermission) {
        Role role = this.roleRepository.findByName(savePermission.getRole()).orElseThrow(() -> new ObjectNotFoundException("role not found with role "+ savePermission.getRole()));
        Operation operation = this.operationRepository.findByName(savePermission.getOperation()).orElseThrow(() -> new ObjectNotFoundException("Operation not found with operation:" + savePermission.getOperation()));

        GrantedPermission grantedPermissionTosave = new GrantedPermission();
        grantedPermissionTosave.setOperation(operation);
        grantedPermissionTosave.setRole(role);
        
        GrantedPermission permissionSaved = this.permissionRepository.save(grantedPermissionTosave);
         return this.mapEntityToShowDto(permissionSaved);
    }

    @Override
    public boolean removeOnePermission(SavePermission savePermission) {
        Role role = this.roleRepository.findByName(savePermission.getRole()).orElseThrow(() -> new ObjectNotFoundException("role not found with role "+ savePermission.getRole()));
        Operation operation = this.operationRepository.findByName(savePermission.getOperation()).orElseThrow(() -> new ObjectNotFoundException("Operation not found with operation:" + savePermission.getOperation()));
        
        
        GrantedPermission grantedPermission = permissionRepository.findByRoleIdAndOperationId(role.getId(), operation.getId())
        .orElseThrow(() -> new ObjectNotFoundException("Operation not found with operation:" + savePermission.getOperation()+ savePermission.getRole()));
        try {
            this.permissionRepository.deleteById(grantedPermission.getId());
            return true;
        } catch (Exception e) {
            return false;
        }
        

    }
    
}
