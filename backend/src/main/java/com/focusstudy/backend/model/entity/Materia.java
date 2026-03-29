package com.focusstudy.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List; // Importante: Não esqueça desse import!

@Entity
@Table(name = "materias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Materia {

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    // Relacionamento Bidirecional
    // mappedBy = materia indica que o dono da relação é o campo 'materia' na classe SessaoEstudo
    // orphanRemoval = true, se a matéria sumir, as sessões "órfãs" somem também
    @JsonIgnore
    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessaoEstudo> sessoes;
}