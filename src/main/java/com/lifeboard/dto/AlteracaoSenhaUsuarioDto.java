package com.lifeboard.dto;

import jakarta.validation.constraints.NotBlank;

public record AlteracaoSenhaUsuarioDto(@NotBlank String senhaAtual,
                                       @NotBlank String novaSenha,
                                       @NotBlank String novaSenhaConfirmacao) {
}
