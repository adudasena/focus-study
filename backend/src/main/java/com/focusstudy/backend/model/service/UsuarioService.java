package com.focusstudy.backend.model.service;

import com.focusstudy.backend.model.entity.Usuario;
import com.focusstudy.backend.model.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Boolean criarUsuario(Usuario usuario) throws Exception {
        try {
            // Criptografa a senha antes de salvar usando o encoder injetado
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            repository.save(usuario);
            return true;
        } catch (Exception e) {
            throw new Exception("Erro ao salvar usuário.");
        }
    }

    // Este método será chamado pelo CustomUserDetails
    public Usuario buscarPorUsername(String username) {
        return repository.findByUsername(username);
    }
}