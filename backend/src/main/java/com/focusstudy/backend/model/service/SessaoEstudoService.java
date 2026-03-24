package com.focusstudy.backend.model.service;

import com.focusstudy.backend.model.entity.SessaoEstudo;
import com.focusstudy.backend.model.repositories.SessaoEstudoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SessaoEstudoService {

    @Autowired
    private SessaoEstudoRepository repository;

    public List<SessaoEstudo> listarTodas() {
        return repository.findAll();
    }

    public SessaoEstudo salvar(SessaoEstudo sessao) {
        return repository.save(sessao);
    }
}