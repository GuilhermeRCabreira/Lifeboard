package com.lifeboard.domain.area.dto;

import jakarta.validation.constraints.NotBlank;

public record AreaCadastroDto(
        @NotBlank(message = "O nome da área é obrigatório")
        String nome
) {
}
