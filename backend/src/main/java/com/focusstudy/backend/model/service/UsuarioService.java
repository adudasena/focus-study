package com.focusstudy.backend.model.service;

import com.focusstudy.backend.model.entity.Papel;
import com.focusstudy.backend.model.entity.Usuario;
import com.focusstudy.backend.model.repositories.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager; // Ele substitui a necessidade do PapelRepository para buscas simples

    @Transactional // Importante para garantir que a busca e o salvamento funcionem juntos
    public Boolean criarUsuario(Usuario usuario) throws Exception {
        try {
            // 1. Criptografa a senha
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            // 2. Busca o papel 'USER' no banco usando uma query simples
            // Isso evita criar um Repository novo agora
            Papel papelUser = entityManager.createQuery("SELECT p FROM papeis p WHERE p.papel = :nome", Papel.class)
                    .setParameter("nome", "USER")
                    .getSingleResult();

            // 3. Atribui o papel ao usuário
            List<Papel> papeis = new ArrayList<>();
            papeis.add(papelUser);
            usuario.setPapeis(papeis);

            // 4. Salva o usuário já com o papel
            repository.save(usuario);
            return true;
        } catch (Exception e) {
            // Se o papel 'USER' não existir no banco, ele vai cair aqui
            throw new Exception("Erro ao salvar usuário: Certifique-se de que o papel USER existe no banco.");
        }
    }

    public Usuario buscarPorUsername(String username) {
        return repository.findByUsername(username);
    }
}