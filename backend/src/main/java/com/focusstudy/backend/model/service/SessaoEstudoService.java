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

    // Listar apenas as sessões do usuário logado
    public List<SessaoEstudo> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    // dashboard filtrado por usuário
    public List<EstatisticaDTO> obterEstatisticas(Long usuarioId) {
        return repository.findTempoTotalPorMateria(usuarioId);
    }

    // dashboard pro admin
    public List<EstatisticaDTO> obterEstatisticasGerais() {
        return repository.findTempoTotalGeral();
    }

    public SessaoEstudo salvar(SessaoEstudo sessao) {
        return repository.save(sessao);
    }

    // Busca a última sessão especificamente DESTE usuário
    public Optional<SessaoEstudo> buscarUltimaSessao(Long usuarioId) {
        return repository.findFirstByUsuarioIdOrderByStartTimeDesc(usuarioId);
    }
}