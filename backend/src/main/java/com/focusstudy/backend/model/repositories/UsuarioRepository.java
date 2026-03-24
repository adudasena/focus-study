package com.focusstudy.backend.model.repositories;

import com.focusstudy.backend.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}

// O Spring vai criar o SQL: SELECT * FROM usuarios WHERE username = ?
// Retornamos a nossa Entidade Usuario, e não UserDetails.
