package com.focusstudy.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table (name = "sessoes")
@NoArgsConstructor
@AllArgsConstructor

public class SessaoEstudo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime; // Data e hora do início do estudo

    private Integer durationMinutes; // Duração em minutos

    @ManyToOne // Muitas sessões podem pertencer a uma única matéria
    @JoinColumn(name = "materia_id")
    private Materia materia;

    // Cada sessão a um usuário
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}