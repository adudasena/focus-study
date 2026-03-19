package com.studyflow.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {
    @Id
    @GeneratedValue
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

}
