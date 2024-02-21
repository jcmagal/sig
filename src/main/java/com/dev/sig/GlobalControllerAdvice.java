package com.dev.sig;

import org.springframework.ui.Model;
import com.dev.sig.models.Funcionario;
import com.dev.sig.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private FuncionarioService funcionarioService;

    @ModelAttribute
    public void addAttributesToModel(Model model) {
        Funcionario funcionarioAtual = funcionarioService.getFuncionarioAtual();
        if (funcionarioAtual != null) {
            String nomeCompleto = funcionarioAtual.getNome();
            String[] partesDoNome = nomeCompleto.split("\\s+");
            String nomeParaExibir = partesDoNome[0]; // Primeiro nome
            if (partesDoNome.length > 1) {
                nomeParaExibir += " " + partesDoNome[1]; // Segundo nome
            }
            model.addAttribute("nomeUsuario", nomeParaExibir);
        }
    }
}
