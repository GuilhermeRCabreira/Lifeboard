package com.lifeboard.controller;

import com.lifeboard.domain.area.Area;
import com.lifeboard.domain.area.AreaService;
import com.lifeboard.domain.area.dto.AreaAtualizacaoDto;
import com.lifeboard.domain.area.dto.AreaCadastroDto;
import com.lifeboard.domain.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/areas")
public class AreaController {

    private static final String PAGINA_LISTA = "areas/lista";
    private static final String PAGINA_FORMULARIO = "areas/formulario";
    private static final String REDIRECT_LISTA = "redirect:/areas";

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping
    public String listar(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        List<Area> areas = areaService.listar(usuarioLogado);
        model.addAttribute("areas", areas);
        return PAGINA_LISTA;
    }

    @GetMapping("/nova")
    public String carregarFormularioCriacao(Model model) {
        model.addAttribute("areaDto", new AreaCadastroDto(""));
        model.addAttribute("acao", "/areas/nova");
        return PAGINA_FORMULARIO;
    }

    @PostMapping("/nova")
    public String criar(@Valid @ModelAttribute("areaDto") AreaCadastroDto dto, BindingResult result,
                        Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        if (result.hasErrors()) {
            model.addAttribute("acao", "/areas/nova");
            return PAGINA_FORMULARIO;
        }

        try {
            areaService.cadastrar(dto, usuarioLogado);
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("acao", "/areas/nova");
            return PAGINA_FORMULARIO;
        }

        return REDIRECT_LISTA;
    }

    @GetMapping("/{id}/editar")
    public String carregarFormularioEdicao(@PathVariable Long id, Model model,
                                           @AuthenticationPrincipal Usuario usuarioLogado) {
        Area area = areaService.buscarPorId(id, usuarioLogado);
        model.addAttribute("areaDto", new AreaAtualizacaoDto(area.getNome()));
        model.addAttribute("acao", "/areas/" + id + "/editar");
        return PAGINA_FORMULARIO;
    }

    @PostMapping("/{id}/editar")
    public String editar(@PathVariable Long id, @Valid @ModelAttribute("areaDto") AreaAtualizacaoDto dto,
                         BindingResult result, Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        if (result.hasErrors()) {
            model.addAttribute("acao", "/areas/" + id + "/editar");
            return PAGINA_FORMULARIO;
        }

        try {
            areaService.atualizar(id, dto, usuarioLogado);
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("acao", "/areas/" + id + "/editar");
            return PAGINA_FORMULARIO;
        }

        return REDIRECT_LISTA;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado) {
        areaService.excluir(id, usuarioLogado);
        return REDIRECT_LISTA;
    }
}