package com.institucion.config;

import com.institucion.entity.Usuario;
import com.institucion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        usuarioRepository.findByUsername("admin").ifPresent(usuario -> {
            if (!usuario.getPassword().startsWith("$2a$")) {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                usuarioRepository.save(usuario);
                System.out.println(">>> Password del admin encriptado correctamente");
            } else {
                System.out.println(">>> Password del admin ya estaba encriptado");
            }
        });
    }
}