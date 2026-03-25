package com.focusstudy.backend.model.repositories;

import com.focusstudy.backend.controller.DTO.EstatisticaDTO;
import com.focusstudy.backend.model.entity.SessaoEstudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SessaoEstudoRepository extends JpaRepository<SessaoEstudo, Long> {
    //pro dashboard funcionar
    //query criando objeto DTO direto do banco
    @Query("SELECT new com.focusstudy.backend.controller.DTO.EstatisticaDTO(s.materia.name, s.materia.color, SUM(s.durationMinutes)) " +
            "FROM SessaoEstudo s GROUP BY s.materia.name, s.materia.color")
    List<EstatisticaDTO> findTempoTotalPorMateria();
}