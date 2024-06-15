package com.master.api.spring.security.master.util;

import java.util.Arrays;
import java.util.List;

public enum RoleEnum {
    //... Cada rol tendera un array de permisos que obtendrá desde el enum de RolPermission
    ADMIN(Arrays.asList(
        RolPermissionEnum.READ_ALL_PRODUCTS,
        RolPermissionEnum.READ_ONE_PRODUCT,
        RolPermissionEnum.CREATE_ONE_PRODUCT,
        RolPermissionEnum.UPDATE_ONE_PRODUCT,
        RolPermissionEnum.DISABLE_ONE_PRODUCT,

        // CATEGORY
        RolPermissionEnum.READ_ALL_CATEGORYS,
        RolPermissionEnum.READ_ONE_CATEGORY,
        RolPermissionEnum.CREATE_ONE_CATEGORY,
        RolPermissionEnum.UPDATE_ONE_CATEGORY,
        RolPermissionEnum.DISABLE_ONE_CATEGORY,
        RolPermissionEnum.READ_MY_PROFILE
    )),
    ASSISTANT_ADIM(Arrays.asList(
        RolPermissionEnum.READ_ALL_PRODUCTS,
        RolPermissionEnum.READ_ONE_PRODUCT,
        RolPermissionEnum.UPDATE_ONE_PRODUCT,
 
        // CATEGORY
        RolPermissionEnum.READ_ALL_CATEGORYS,
        RolPermissionEnum.READ_ONE_CATEGORY,
        RolPermissionEnum.UPDATE_ONE_CATEGORY,
        RolPermissionEnum.READ_MY_PROFILE
    )),
    CUSTOMER(Arrays.asList(RolPermissionEnum.READ_MY_PROFILE));
    

    //== Este es el constructor que recibe una lista de permisos para asignarlos a la lista permissions
    RoleEnum (List<RolPermissionEnum> permissions){
        this.permissions = permissions;
    }
    private List<RolPermissionEnum> permissions;

    public List<RolPermissionEnum> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolPermissionEnum> permissions) {
        this.permissions = permissions;
    }

    
}
