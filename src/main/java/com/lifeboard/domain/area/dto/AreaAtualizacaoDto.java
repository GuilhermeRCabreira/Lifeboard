package com.lifeboard.domain.area.dto;

import jakarta.validation.constraints.NotBlank;

public record AreaAtualizacaoDto(
        @NotBlank(message = "O nome da área é obrigatório")
        String nome
) {
}
