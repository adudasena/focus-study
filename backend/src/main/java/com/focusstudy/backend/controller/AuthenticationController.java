package com.focusstudy.backend.controller;

import com.focusstudy.backend.model.Usuario;
import com.focusstudy.backend.model.repositories.UsuarioRepository;
import com.focusstudy.backend.model.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Usuario data) {
        // Note que aqui usamos getUsername() e getPassword() para autenticar
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Usuario data) {
        // 1. Verifica se o usuário já existe usando o novo nome do campo
        if(this.repository.findByUsername(data.getUsername()) != null) return ResponseEntity.badRequest().build();

        // 2. Criptografa a senha que veio do Postman
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());

        // 3. USA O SETTER PARA SALVAR A SENHA CRIPTOGRAFADA (Aqui estava o seu erro!)
        data.setPassword(encryptedPassword);

        // 4. Salva no banco
        this.repository.save(data);

        return ResponseEntity.ok().build();
    }
}