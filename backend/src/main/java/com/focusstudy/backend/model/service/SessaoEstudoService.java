package com.focusstudy.backend.model.service;

import com.focusstudy.backend.controller.DTO.EstatisticaDTO;
import com.focusstudy.backend.model.entity.SessaoEstudo;
import com.focusstudy.backend.model.repositories.SessaoEstudoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SessaoEstudoService {

    @Autowired
    private SessaoEstudoRepository repository;

    public List<SessaoEstudo> listarTodas() {
        return repository.findAll();
    }

    //metodo pra chamar a query do dashboard (da repository)
    public List<EstatisticaDTO> obterEstatisticas() {
        return repository.findTempoTotalPorMateria();
    }

    public SessaoEstudo salvar(SessaoEstudo sessao) {
        return repository.save(sessao);
    }

    public Optional<SessaoEstudo> buscarUltimaSessao() {
        return repository.findFirstByOrderByStartTimeDesc();
    }
}