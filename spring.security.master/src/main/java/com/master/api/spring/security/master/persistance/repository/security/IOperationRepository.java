package com.master.api.spring.security.master.persistance.repository.security;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.master.api.spring.security.master.persistance.entity.Security.Operation;

@Repository
public interface IOperationRepository extends JpaRepository<Operation, Long> {
    
    @Query("SELECT o FROM Operation o where o.permit_all = true") // obtiene u na lista de operaciones cuando la columna permite_all = true
    List<Operation> findByPublisAcces();

    Optional<Operation>findByName(String name);
}
