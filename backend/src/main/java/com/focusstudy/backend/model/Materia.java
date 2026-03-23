package com.focusstudy.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "materias")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Materia {
    @Id
    @GeneratedValue
    private Long id;

}
