package com.sistema;

import com.sistema.modelos.Roles;
import com.sistema.modelos.TiposRoles;
import com.sistema.modelos.Usuario;
import com.sistema.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class SistemaReservasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaReservasApplication.class, args);
	}
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UsuarioRepositorio userRepository;

	@Bean
	CommandLineRunner init(){
		return args -> {

			Usuario userEntity = Usuario.builder()
					.correo("andres@mail.com")
					.usuario("andres")
					.contrasenia(passwordEncoder.encode("1234"))
					.roles(Set.of(Roles.builder()
							.nombre(TiposRoles.valueOf(TiposRoles.ADMIN.name()))
							.build()))
					.build();

			Usuario userEntity2 = Usuario.builder()
					.correo("forero@mail.com")
					.usuario("forero")
					.contrasenia(passwordEncoder.encode("1234"))
					.roles(Set.of(Roles.builder()
							.nombre(TiposRoles.valueOf(TiposRoles.ESTUDIANTE.name()))
							.build()))
					.build();

			userRepository.save(userEntity);
			userRepository.save(userEntity2);
		};
	}
}
