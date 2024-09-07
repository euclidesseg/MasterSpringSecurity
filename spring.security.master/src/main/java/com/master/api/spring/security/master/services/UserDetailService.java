package com.master.api.spring.security.master.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.master.api.spring.security.master.exception.ObjectNotFoundException;
import com.master.api.spring.security.master.persistance.repository.security.IUserRepository;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        return this.userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User not found with username " + username));
    }
    
}