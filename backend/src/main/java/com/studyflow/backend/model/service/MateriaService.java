package com.studyflow.backend.model.service;

import com.studyflow.backend.model.Materia;
import com.studyflow.backend.model.repositories.MateriaRepository;
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