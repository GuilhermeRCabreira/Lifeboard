package com.lifeboard.domain.usuario;

import com.lifeboard.domain.usuario.email.EmailService;
import com.lifeboard.domain.usuario.dto.AlteracaoSenhaUsuarioDto;
import com.lifeboard.domain.usuario.dto.CadastroUsuarioDto;
import com.lifeboard.domain.usuario.dto.DadosRecuperacaoConta;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado!"));
    }

    public void cadastrarUsuario(CadastroUsuarioDto cadastroUsuarioDto) {
        if(usuarioRepository.existsByEmailIgnoreCase(cadastroUsuarioDto.email())) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse email!");
        }

        if (!cadastroUsuarioDto.senha().equals(cadastroUsuarioDto.confirmarSenha())){
            throw new IllegalArgumentException("As senhas não coincidem!");
        }

        String senhaCriptografada = passwordEncoder.encode(cadastroUsuarioDto.senha());
        Usuario usuario = new Usuario(cadastroUsuarioDto.nome(), cadastroUsuarioDto.email(), senhaCriptografada);

        usuarioRepository.save(usuario);

    }

    public void alterarSenha(AlteracaoSenhaUsuarioDto senhaDto, Usuario usuario) {
        if (!passwordEncoder.matches(senhaDto.senhaAtual(), usuario.getPassword())){
            throw new IllegalArgumentException("Senha digitada não confere com a senha atual!");
        }
        if (!senhaDto.novaSenha().equals(senhaDto.novaSenhaConfirmacao())){
            throw new IllegalArgumentException("As senhas não coincidem!");
        }

        String senhaCriptografada = passwordEncoder.encode(senhaDto.novaSenha());
        usuario.alterarSenha(senhaCriptografada);

        usuarioRepository.save(usuario);

    }

    public void enviarToken(String email) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email).orElseThrow(
                () -> new EntityNotFoundException("Usuário não encontrado!")
        );

        String token = UUID.randomUUID().toString();
        usuario.setToken(token);
        usuario.setExpiracaoToken(LocalDateTime.now().plusMinutes(15));

        usuarioRepository.save(usuario);
        emailService.enviarEmailSenha(usuario);
    }

    public void recuperarConta(String codigo, DadosRecuperacaoConta dados) {

        Usuario usuario = usuarioRepository.findByTokenIgnoreCase(codigo).orElseThrow(
                ()-> new IllegalArgumentException("Link inválido")
        );

        if (usuario.getExpiracaoToken().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Link expirado!");
        }

        if (!dados.novaSenha().equals(dados.novaSenhaConfirmacao())){
            throw new IllegalArgumentException("As senhas não coincidem!");
        }


        String senhaCriptografada = passwordEncoder.encode(dados.novaSenha());
        usuario.alterarSenha(senhaCriptografada);

        usuario.setExpiracaoToken(null);
        usuario.setToken(null);

        usuarioRepository.save(usuario);
    }
}
