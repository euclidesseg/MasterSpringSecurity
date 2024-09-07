package com.master.api.spring.security.master.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.master.api.spring.security.master.dto.SaveUser;
import com.master.api.spring.security.master.exception.InvalidPasswordException;
import com.master.api.spring.security.master.exception.ObjectNotFoundException;
import com.master.api.spring.security.master.interfaces.IUserService;
import com.master.api.spring.security.master.persistance.entity.Security.Role;
import com.master.api.spring.security.master.persistance.entity.Security.User;
import com.master.api.spring.security.master.persistance.repository.security.IUserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RoleService roleService;

    @Override
    public User createOneCustomer(SaveUser newUser) {

        // == validamos contraseña
        validatePassword(newUser);

        User user = new User();
        // == Seteamos el password
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setUsername(newUser.getUsername());
        user.setName(newUser.getName());



        Role roleDefault = roleService.findDefaultRole().orElseThrow(() -> new ObjectNotFoundException("Role Not found whit"));
        user.setRole(roleDefault);
        user.setEnabled(true);
        // if (newUser.getPassword().equals("marianito")) {
            //     user.setRole(Role.CUSTOMER);
        // } else if (newUser.getPassword().equals("fulanito")) {
        //     user.setRole(Role.ASSISTANT_ADIM);
        // }else if (newUser.getPassword().equals("marquecito") ) {
        //     user.setRole(Role.ADMIN);
        // }

        return this.userRepository.save(user);
    }

    public Optional<User> findOneByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    private void validatePassword(SaveUser newUser) {
        if (newUser.getPassword().toString() == null || newUser.getRepeatPassword().toString() == null) {
            throw new InvalidPasswordException("Password don't match");
        }
        if (!newUser.getPassword().toString().equals(newUser.getRepeatPassword().toString())) {
            throw new InvalidPasswordException("Password don't match");
        }
    }

    public Optional<User> readMyProfileByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
