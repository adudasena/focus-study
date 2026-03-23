package com.focusstudy.backend.controller;

import com.focusstudy.backend.model.SessaoEstudo;
import com.focusstudy.backend.model.service.SessaoEstudoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sessoes") // URL: localhost:8080/sessoes
public class SessaoEstudoController {

    @Autowired
    private SessaoEstudoService service;

    @GetMapping
    public ResponseEntity<List<SessaoEstudo>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody SessaoEstudo sessao) {
        service.salvar(sessao);
        return ResponseEntity.ok().build();
    }
}