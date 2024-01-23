package com.sistema.repositorio;

import com.sistema.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);
    @Query("select u from Usuario u where u.usuario = ?1")
    Optional<Usuario> getName(String usuario);
}
