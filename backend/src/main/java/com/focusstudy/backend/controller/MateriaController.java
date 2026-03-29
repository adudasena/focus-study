package com.focusstudy.backend.controller;

import com.focusstudy.backend.model.entity.Materia;
import com.focusstudy.backend.model.entity.Usuario;
import com.focusstudy.backend.model.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    @Autowired
    private MateriaService service;

    @PostMapping("/novo")
    public ResponseEntity<Boolean> salvar(@RequestBody Materia materia, @AuthenticationPrincipal Usuario logado) {
        try {
            materia.setUsuario(logado);
            service.salvar(materia);
            return ResponseEntity.status(201).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(false);
        }
    }

    @GetMapping
    public ResponseEntity<List<Materia>> listar(@AuthenticationPrincipal Usuario logado) {
        // Se o usuário for ADMIN, ele vê TUDO. Se for USER, vê só as DELE.
        if (logado.getPapeis().stream().anyMatch(p -> p.getPapel().equals("ADMIN"))) {
            return ResponseEntity.ok(service.listarTodas()); // Método que dá repository.findAll()
        }

        return ResponseEntity.ok(service.listarPorUsuario(logado.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> atualizar(@PathVariable Long id, @RequestBody Materia materia) {
        try {
            service.atualizar(id, materia); // O service deve ter esse método de update
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(false);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> excluir(@PathVariable Long id) {
        try {
            service.excluir(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(false);
        }
    }
}