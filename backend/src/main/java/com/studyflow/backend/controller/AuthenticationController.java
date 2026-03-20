package com.studyflow.backend.controller;

import com.studyflow.backend.model.Usuario;
import com.studyflow.backend.model.repositories.UsuarioRepository;
import com.studyflow.backend.model.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(token); // Retorna o token para o Vue.js guardar
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Usuario data) {
        if(this.repository.findByLogin(data.getLogin()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getSenha());
        data.setSenha(encryptedPassword); // Salva a senha protegida!

        this.repository.save(data);
        return ResponseEntity.ok().build();
    }
}