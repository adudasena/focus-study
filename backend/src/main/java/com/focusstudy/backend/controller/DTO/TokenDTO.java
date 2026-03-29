package com.focusstudy.backend.controller.DTO;

public class TokenDTO {
    private String token;
    private String nome;
    private String role;
    private Long id;

    public TokenDTO(String token, String nome, String role, Long id) {
        this.token = token;
        this.nome = nome;
        this.role = role;
        this.id= id;
    }

    public String getToken() {
        return token; }
    public void setToken(String token) {
        this.token = token; }

    public String getNome() {
        return nome; }
    public void setNome(String nome) {
        this.nome = nome; }

    public String getRole() {
        return role; }
    public void setRole(String role) {
        this.role = role; }

    public Long getId() {
        return id;
    }

}