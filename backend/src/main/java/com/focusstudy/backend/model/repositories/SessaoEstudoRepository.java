package com.focusstudy.backend.model.repositories;

import com.focusstudy.backend.controller.DTO.EstatisticaDTO;
import com.focusstudy.backend.model.entity.SessaoEstudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SessaoEstudoRepository extends JpaRepository<SessaoEstudo, Long> {

    // 1. Dashboard Filtrado: Agora só soma as matérias do usuário logado
    @Query("SELECT new com.focusstudy.backend.controller.DTO.EstatisticaDTO(s.materia.name, s.materia.color, SUM(s.durationSeconds)) " +
            "FROM SessaoEstudo s WHERE s.usuario.id = :usuarioId " +
            "GROUP BY s.materia.name, s.materia.color")
    List<EstatisticaDTO> findTempoTotalPorMateria(Long usuarioId);

    @Query("SELECT new com.focusstudy.backend.controller.DTO.EstatisticaDTO(s.materia.name, s.materia.color, SUM(s.durationSeconds)) " +
            "FROM SessaoEstudo s GROUP BY s.materia.name, s.materia.color")
    List<EstatisticaDTO> findTempoTotalGeral();

    // 2. Última Sessão Filtrada: Pega a última sessão especificamente DESTE usuário
    java.util.Optional<SessaoEstudo> findFirstByUsuarioIdOrderByStartTimeDesc(Long usuarioId);

    // 3. Método padrão para listar todas as sessões de um usuário (caso precise)
    List<SessaoEstudo> findByUsuarioId(Long usuarioId);
}