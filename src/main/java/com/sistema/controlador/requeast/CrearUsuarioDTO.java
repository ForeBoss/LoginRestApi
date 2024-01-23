package com.sistema.controlador.requeast;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearUsuarioDTO {

    @Email
    @NotBlank
    private String correo;
    @NotBlank
    private String usuario;
    @NotBlank
    private String contrasenia;
    private Set<String> roles;

}
