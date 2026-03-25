package com.focusstudy.backend.controller
        .DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EstatisticaDTO {
    private String nome;
    private String cor;
    private Long segundosTotais; //agora em segundos
}