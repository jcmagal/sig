package com.dev.sig.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.dev.sig.repository.FuncionarioRepository;
import com.dev.sig.models.Funcionario;

import java.util.Collections;

@Service
public class FuncionarioService implements UserDetailsService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Implementação do método da interface UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByCpfIncludingCargo(cpf)
                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não encontrado com CPF: " + cpf));

        // Adiciona o prefixo "ROLE_" ao nome do NivelAcesso
        String authority = funcionario.getCargo() != null ? "ROLE_" + funcionario.getCargo().getNivelAcesso().name() : "ROLE_SEM_ACESSO";

        return new User(funcionario.getCpf(), funcionario.getSenha(), Collections.singletonList(new SimpleGrantedAuthority(authority)));
    }

    public Funcionario getFuncionarioAtual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
            return null;
        }
        String cpf = authentication.getName(); // Supondo que o nome de usuário é o CPF do funcionário
        return funcionarioRepository.findByCpfIncludingCargo(cpf).orElse(null);
    }

}
