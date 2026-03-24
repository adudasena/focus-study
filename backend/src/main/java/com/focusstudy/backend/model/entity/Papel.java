package com.focusstudy.backend.model.entity;

import jakarta.persistence.*;

@Entity(name = "papeis")
public class Papel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String papel;

    public Papel() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPapel() { return papel; }
    public void setPapel(String papel) { this.papel = papel; }
}