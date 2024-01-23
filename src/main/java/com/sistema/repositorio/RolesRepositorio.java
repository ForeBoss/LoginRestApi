package com.sistema.repositorio;

import com.sistema.modelos.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepositorio extends JpaRepository<Roles, Long> {
}
