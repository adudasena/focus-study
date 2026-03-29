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
            var authenticationToken = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword());
            Authentication authentication = manager.authenticate(authenticationToken);

            // gera o token
            String token = tokenService.gerarToken((UserDetails) authentication.getPrincipal());

            // busca o usuário completo no banco para pegar a Role e o Nome real
            Usuario userLogado = usuarioService.buscarPorUsername(usuario.getUsername());

            // pegamos a primeira role da lista
            String role = userLogado.getPapeis().get(0).getPapel();

            // retorna TUDO para o Vue
            return ResponseEntity.ok(new TokenDTO(
                    token,
                    userLogado.getUsername(),
                    role,
                    userLogado.getId()
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao autenticar: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        try {
            // O segredo está aqui dentro: o service vai preparar o usuário antes de salvar
            usuarioService.criarUsuario(usuario);
            return ResponseEntity.status(201).body(true);
        } catch (Exception e) {
            // Se der erro (ex: usuário já existe), ele cai aqui
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}