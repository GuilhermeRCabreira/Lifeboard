package com.lifeboard.domain.area;

import com.lifeboard.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AreaRepository extends JpaRepository<Area, Long> {

    boolean existsByNomeIgnoreCaseAndUsuario(String nome, Usuario usuario);

    List<Area> findAllByUsuario(Usuario usuario);

    Optional<Area> findByIdAndUsuario(Long id, Usuario usuario);

}
