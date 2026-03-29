package com.focusstudy.backend.controller;

import com.focusstudy.backend.controller.DTO.EstatisticaDTO;
import com.focusstudy.backend.model.entity.SessaoEstudo;
import com.focusstudy.backend.model.entity.Usuario;
import com.focusstudy.backend.model.service.SessaoEstudoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sessoes") // URL: localhost:8080/sessoes
public class SessaoEstudoController {

    @Autowired
    private SessaoEstudoService service;

    @GetMapping
    public ResponseEntity<List<SessaoEstudo>> listar(@AuthenticationPrincipal Usuario logado) {
        return ResponseEntity.ok(service.listarPorUsuario(logado.getId()));
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<List<EstatisticaDTO>> obterEstatisticas(@AuthenticationPrincipal Usuario logado) {
        // Verifica se o usuário logado tem o papel de ADMIN
        boolean isAdmin = logado.getPapeis().stream()
                .anyMatch(p -> p.getPapel().equals("ADMIN"));

        if (isAdmin) {
            return ResponseEntity.ok(service.obterEstatisticasGerais());
        }
        return ResponseEntity.ok(service.obterEstatisticas(logado.getId()));
    }

    @GetMapping("/ultima")
    public ResponseEntity<SessaoEstudo> buscarUltima(@AuthenticationPrincipal Usuario logado) {
        return service.buscarUltimaSessao(logado.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/novo")
    public ResponseEntity<Boolean> salvar(@RequestBody SessaoEstudo sessao, @AuthenticationPrincipal Usuario logado) {
        try {
            // Garante que a sessão seja salva com o dono correto
            sessao.setUsuario(logado);
            service.salvar(sessao);
            return ResponseEntity.status(201).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(false);
        }
    }
}