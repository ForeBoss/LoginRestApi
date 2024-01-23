package com.sistema.servicios;

import com.sistema.modelos.Usuario;
import com.sistema.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UsuarioDetallesServicioImp implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario userDetails = usuarioRepositorio.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException(("El usuario".concat(username).concat("no existe"))));

        Collection<? extends GrantedAuthority> authorities = userDetails.getRoles().stream().map(rol -> new SimpleGrantedAuthority("ROLE".concat(rol.getNombre().name()))).collect(Collectors.toSet());

        return new User(userDetails.getUsuario(),
                userDetails.getContrasenia(),
                true,
                true,
                true,
                true,
                authorities
        );
    }
}
