package com.lifeboard.domain.area;

import com.lifeboard.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(
        name="areas",
        uniqueConstraints = @UniqueConstraint(
                name="uk_areas_nome_usuario",
        columnNames = {"nome", "usuario_id"}
    )
)
public class Area {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="usuario_id", nullable=false)
    private Usuario usuario;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Area() {

    }

    public Area(String nome, Usuario usuario) {
        this.nome = nome;
        this.usuario = usuario;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void atualizarNome(@NotBlank(message = "O nome da área é obrigatório") String nome) {

        this.nome = nome;
    }
}
