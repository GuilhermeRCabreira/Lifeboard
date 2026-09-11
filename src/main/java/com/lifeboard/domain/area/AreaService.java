package com.lifeboard.domain.area;

import com.lifeboard.domain.area.dto.AreaAtualizacaoDto;
import com.lifeboard.domain.area.dto.AreaCadastroDto;
import com.lifeboard.domain.usuario.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public Area cadastrar(AreaCadastroDto dto, Usuario usuario) {

        if (areaRepository.existsByNomeIgnoreCaseAndUsuario(dto.nome(), usuario)){
            throw new IllegalArgumentException("Você já possui área com esse nome.");
        }

        Area area = new Area(dto.nome(), usuario);
        return areaRepository.save(area);
    }

    public List<Area> listar(Usuario usuario){
        return areaRepository.findAllByUsuario(usuario);
    }

    public Area buscarPorId(Long id, Usuario usuario){
        return areaRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(()->new EntityNotFoundException("Área não encontrada."));
    }

    public Area atualizar(Long id, AreaAtualizacaoDto dto, Usuario usuario){
        Area area = buscarPorId(id, usuario);

        boolean nomeAlterado = !area.getNome().equalsIgnoreCase(dto.nome());
        if (nomeAlterado && areaRepository.existsByNomeIgnoreCaseAndUsuario(dto.nome(), usuario)){
            throw new IllegalArgumentException("Você já possui uma área com esse nome.");
        }

        area.atualizarNome(dto.nome());
        return areaRepository.save(area);

    }

    public void excluir(Long id, Usuario usuario){
        Area area = buscarPorId(id, usuario);
        areaRepository.delete(area);
    }
}
