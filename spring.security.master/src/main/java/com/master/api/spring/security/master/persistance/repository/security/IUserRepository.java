package com.master.api.spring.security.master.persistance.repository.security;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.master.api.spring.security.master.persistance.entity.Security.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
