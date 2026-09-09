package com.lifeboard.controller;

import com.lifeboard.domain.usuario.Usuario;
import com.lifeboard.domain.usuario.UsuarioService;
import com.lifeboard.dto.AlteracaoSenhaUsuarioDto;
import com.lifeboard.dto.CadastroUsuarioDto;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    private static final String PAGINA_CADASTRO = "autenticacao/cadastro";
    private static final String REDIRECT_LOGIN = "redirect:/login?sucesso";
    private static final String PAGINA_ALTERACAO_SENHA = "autenticacao/alterar-senha";

    private final UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/cadastro")
    public String carregaPaginaCadastro(Model model){
        model.addAttribute("usuarioDto", new CadastroUsuarioDto("", "", "", ""));
        return PAGINA_CADASTRO;
    }

    @PostMapping("/cadastro")
    public String cadastrar(@Valid @ModelAttribute CadastroUsuarioDto cadastroUsuarioDto, BindingResult result,
                            Model model){

        if (result.hasErrors()){
            return PAGINA_CADASTRO;
        }

        try{
            usuarioService.cadastrarUsuario(cadastroUsuarioDto);
        } catch (IllegalArgumentException e){
            model.addAttribute("erro", e.getMessage());
            return PAGINA_CADASTRO;
        }

        return REDIRECT_LOGIN;
    }

    @GetMapping("/alterar-senha")
    public String carregaPaginaAlteracao(Model model){
        model.addAttribute("alterarSenhaDto", new AlteracaoSenhaUsuarioDto("", "", ""));
        return PAGINA_ALTERACAO_SENHA;
    }

    @PostMapping("/alterar-senha")
    public String alterarSenha(@Valid @ModelAttribute AlteracaoSenhaUsuarioDto alteracaoSenhaUsuarioDto, BindingResult result,
                            Model model,
                               @AuthenticationPrincipal Usuario usuario){

        if (result.hasErrors()){
            return PAGINA_ALTERACAO_SENHA;
        }

        try{
            usuarioService.alterarSenha(alteracaoSenhaUsuarioDto, usuario);
            return REDIRECT_LOGIN;
        } catch (IllegalArgumentException e){
            model.addAttribute("erro", e.getMessage());
            return PAGINA_ALTERACAO_SENHA;
        }


    }
}
