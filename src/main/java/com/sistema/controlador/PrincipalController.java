package com.sistema.controlador;

import com.sistema.modelos.Roles;
import com.sistema.modelos.TiposRoles;
import com.sistema.modelos.Usuario;
import com.sistema.repositorio.UsuarioRepositorio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.sistema.controlador.requeast.CrearUsuarioDTO;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class PrincipalController {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hola")
    public String saludo() {
        return "Esta pagina es disponible para todos";
    }

    @GetMapping("/holas")
    public String saludoSi() {
        return "Rey, tienes token, muy bien";
    }

    @GetMapping("/crearUsuario")
    @PreAuthorize("hasRole('ADMIN')")
    public String crearUsuario() {
        // Lógica para la página de crear usuarios
        return "crearUsuario";
    }

    @PostMapping("/crearUsuario")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody CrearUsuarioDTO crearUDTO) {
        Set<Roles> roles = crearUDTO.getRoles().stream()
                .map(ro -> Roles.builder()
                        .nombre(TiposRoles.valueOf(ro))
                        .build())
                .collect(Collectors.toSet());

        Usuario usuario = Usuario.builder()
                .usuario(crearUDTO.getUsuario())
                .contrasenia(passwordEncoder.encode(crearUDTO.getContrasenia()))
                .correo(crearUDTO.getCorreo())
                .roles(roles)
                .build();
        usuarioRepositorio.save(usuario);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("borrarUsuario")
    public String borrarUsuario(@RequestParam String id){
        usuarioRepositorio.deleteById(Long.parseLong(id));
        return "se ha elminado un usuario papa".concat(id);
    }
}
