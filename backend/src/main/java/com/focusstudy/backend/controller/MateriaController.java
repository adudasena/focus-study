package com.focusstudy.backend.controller;

import com.focusstudy.backend.model.entity.Materia;
import com.focusstudy.backend.model.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("materias")
public class MateriaController {

    @Autowired
    private MateriaService service;

    @GetMapping
    public ResponseEntity<List<Materia>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody Materia materia) {
        service.salvar(materia);
        return ResponseEntity.ok().build();
    }
}