package com.focusstudy.backend.controller;

import com.focusstudy.backend.controller.DTO.EstatisticaDTO;
import com.focusstudy.backend.model.entity.SessaoEstudo;
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

    //dashboard
    @GetMapping("/estatisticas")
    public ResponseEntity<List<EstatisticaDTO>> obterEstatisticas() {
        return ResponseEntity.ok(service.obterEstatisticas());
    }

    @GetMapping("/ultima")
    public ResponseEntity<SessaoEstudo> buscarUltima() {
        return service.buscarUltimaSessao()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());

    }

    @PostMapping("/novo")
    public ResponseEntity<Boolean> salvar(@RequestBody SessaoEstudo sessao) {
        try {
            service.salvar(sessao);
            return ResponseEntity.status(201).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(false);
        }

    }
}