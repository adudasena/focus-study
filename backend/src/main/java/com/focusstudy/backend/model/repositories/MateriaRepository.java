package com.focusstudy.backend.model.repositories;

import com.focusstudy.backend.model.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
    // busca todas as matérias que pertencem a um ID de usuário específico
    List<Materia> findByUsuarioId(Long usuarioId);
}