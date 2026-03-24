package com.focusstudy.backend.controller;

import com.focusstudy.backend.controller.DTO.TokenDTO;
import com.focusstudy.backend.model.entity.Usuario;
import com.focusstudy.backend.model.service.TokenService;
import com.focusstudy.backend.model.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> efetuarLogin(@RequestBody Usuario usuario) {
        try {
            // Segue o padrão do prof: usa getUsername e getPassword
            var authenticationToken = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword());
            Authentication authentication = manager.authenticate(authenticationToken);

            // O principal aqui é o UserDetails que o builder criou
            String token = tokenService.gerarToken((UserDetails) authentication.getPrincipal());

            return ResponseEntity.ok(new TokenDTO(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao autenticar: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        try {
            // Usa o seu Service para salvar, igual a lógica de camadas do prof
            usuarioService.criarUsuario(usuario);
            return ResponseEntity.status(201).body(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}