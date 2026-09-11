package com.lifeboard.domain.area.dto;

import com.lifeboard.domain.area.Area;

import java.time.LocalDateTime;

public record AreaRespostaDto(
        Long id,
        String nome,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public AreaRespostaDto(Area area){
        this(area.getId(), area.getNome(), area.getCreatedAt(), area.getUpdatedAt());
    }
}
