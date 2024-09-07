package com.master.api.spring.security.master.persistance.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.master.api.spring.security.master.persistance.entity.Security.JwtToken;

@Repository
public interface IJwtTokenRespository extends JpaRepository<JwtToken, Long>{
    Optional<JwtToken> findByToken(String token);
    
}
