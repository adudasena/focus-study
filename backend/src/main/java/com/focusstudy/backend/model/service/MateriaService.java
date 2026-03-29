package com.focusstudy.backend.model.service;

import com.focusstudy.backend.model.entity.Materia;
import com.focusstudy.backend.model.repositories.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository repository;

    public List<Materia> listarTodas() {
        return repository.findAll(); //admin
    }

    public List<Materia> listarPorUsuario(Long id) {
        return repository.findByUsuarioId(id);
    }

    public Materia salvar(Materia materia) {
        return repository.save(materia);
    }

    // Método para buscar uma matéria específica (útil para validações)
    public Materia buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Método de Atualizar (PUT)
    public Materia atualizar(Long id, Materia dadosNovos) {
        Optional<Materia> materiaOptional = repository.findById(id);

        if (materiaOptional.isPresent()) {
            Materia materiaExistente = materiaOptional.get();
            // atualiza os campos que vêm do DTO/Objeto do Vue
            materiaExistente.setName(dadosNovos.getName());
            materiaExistente.setColor(dadosNovos.getColor());
            return repository.save(materiaExistente);
        }

        return null; // mudar pra exception
    }

    // Método de Excluir (DELETE)
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}