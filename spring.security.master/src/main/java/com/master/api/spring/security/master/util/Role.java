package com.master.api.spring.security.master.util;

import java.util.Arrays;
import java.util.List;

public enum Role {
    //... Cada rol tendera un array de permisos que obtendrá desde el enum de RolPermission
    ADMIN(Arrays.asList(
        RolPermission.READ_ALL_PRODUCTS,
        RolPermission.READ_ONE_PRODUCT,
        RolPermission.CREATE_ONE_PRODUCT,
        RolPermission.UPDATE_ONE_PRODUCT,
        RolPermission.DISABLE_ONE_PRODUCT,

        // CATEGORY
        RolPermission.READ_ALL_CATEGORYS,
        RolPermission.READ_ONE_CATEGORY,
        RolPermission.CREATE_ONE_CATEGORY,
        RolPermission.UPDATE_ONE_CATEGORY,
        RolPermission.DISABLE_ONE_CATEGORY,
        RolPermission.READ_MY_PROFILE
    )),
    ASSISTANT_ADIM(Arrays.asList(
        RolPermission.READ_ALL_PRODUCTS,
        RolPermission.READ_ONE_PRODUCT,
        RolPermission.UPDATE_ONE_PRODUCT,
 
        // CATEGORY
        RolPermission.READ_ALL_CATEGORYS,
        RolPermission.READ_ONE_CATEGORY,
        RolPermission.UPDATE_ONE_CATEGORY,
        RolPermission.READ_MY_PROFILE
    )),
    CUSTOMER(Arrays.asList(RolPermission.READ_MY_PROFILE));
    

    //== Este es el constructor que recibe una lista de permisos para asignarlos a la lista permissions
    Role (List<RolPermission> permissions){
        this.permissions = permissions;
    }
    private List<RolPermission> permissions;

    public List<RolPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolPermission> permissions) {
        this.permissions = permissions;
    }

    
}
