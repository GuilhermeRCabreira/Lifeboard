package com.lifeboard.domain.usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    private String token;

    private LocalDateTime expiracaoToken;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;


    public Usuario(){}

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }


    public String getNome() {
        return nome;
    }

    public Long getId() {
        return id;
    }


    public LocalDateTime getExpiracaoToken() {
        return expiracaoToken;
    }

    public void setExpiracaoToken(LocalDateTime expiracaoToken) {
        this.expiracaoToken = expiracaoToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void alterarSenha(String senhaCriptografada) {
        this.senha = senhaCriptografada;
    }
}







