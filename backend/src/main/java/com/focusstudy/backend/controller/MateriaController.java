package com.focusstudy.backend.controller;

import com.focusstudy.backend.model.entity.Materia;
import com.focusstudy.backend.model.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    @Autowired
    private MateriaService service;

    @PostMapping("/novo")
    public ResponseEntity<Boolean> salvar(@RequestBody Materia materia) {
        try {
            service.salvar(materia);
            return ResponseEntity.status(201).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(false);
        }
    }

    @GetMapping
    public ResponseEntity<List<Materia>> listar() {
        List<Materia> materias = service.listarTodas();
        return ResponseEntity.status(200).body(materias);
    }
}