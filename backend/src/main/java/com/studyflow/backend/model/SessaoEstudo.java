package com.studyflow.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table (name = "sessoes")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class SessaoEstudo {
    @Id
    @GeneratedValue
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

}
