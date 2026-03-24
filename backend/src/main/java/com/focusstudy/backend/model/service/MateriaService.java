package com.focusstudy.backend.model.service;

import com.focusstudy.backend.model.entity.Materia;
import com.focusstudy.backend.model.repositories.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository repository;

    public List<Materia> listarTodas() {
        return repository.findAll();
    }

    public Materia salvar(Materia materia) {
        return repository.save(materia);
    }
}