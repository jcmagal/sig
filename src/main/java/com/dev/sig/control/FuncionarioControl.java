package com.dev.sig.control;



import com.dev.sig.models.Funcionario;
import com.dev.sig.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.dev.sig.models.Endereco;
import com.dev.sig.models.Cidade;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class FuncionarioControl {
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping("/administrative/funcionarios/cadastrar")
    public ModelAndView cadastrar(Funcionario funcionario) {
        ModelAndView mv = new ModelAndView("administrative/funcionarios/cadastro");
        if (funcionario.getEndereco() == null) {
            funcionario.setEndereco(new Endereco());
        }
        mv.addObject("listaEstados", estadoRepository.findAll());
        mv.addObject("listaEndereco", enderecoRepository.findAll());
        mv.addObject("listaCidades", cidadeRepository.findAll());
        mv.addObject("listaCargos", cargoRepository.findAll());
        mv.addObject("funcionario", funcionario != null ? funcionario : new Funcionario());
        return mv;
    }

    @GetMapping("/administrative/funcionarios/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrative/funcionarios/lista");
        mv.addObject("listaFuncionarios", funcionarioRepository.findAll());
        mv.addObject("listaCidades", cidadeRepository.findAll());
        mv.addObject("listaCargos", cargoRepository.findAll());
        return mv;
    }

    @GetMapping("/administrative/funcionarios/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/administrative/funcionarios/listar");
        funcionarioRepository.findById(id).ifPresentOrElse(funcionario -> {
            funcionarioRepository.delete(funcionario);
            // Aqui você pode adicionar uma mensagem de confirmação usando RedirectAttributes
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Funcionário removido com sucesso!");
        }, () -> {
            // E aqui, uma mensagem de erro se o funcionário não for encontrado
            redirectAttributes.addFlashAttribute("mensagemErro", "Funcionário não encontrado!");
        });
        return mv;
    }

    @PostMapping("/administrative/funcionarios/salvar")
    public ModelAndView salvar(@Validated Funcionario funcionario, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(funcionario);
        }

        if (funcionario.getCpf() != null) {
            String cpfSemFormatacao = funcionario.getCpf().replaceAll("\\D", "");
            funcionario.setCpf(cpfSemFormatacao);
        }

        if (funcionario.getEndereco() != null && funcionario.getEndereco().getCep() != null) {
            String cepSemFormatacao = funcionario.getEndereco().getCep().replaceAll("\\D", "");
            funcionario.getEndereco().setCep(cepSemFormatacao);
        }

        if (funcionario.getSenha() != null && !funcionario.getSenha().isEmpty()) {
            String senhaCodificada = passwordEncoder.encode(funcionario.getSenha());
            funcionario.setSenha(senhaCodificada);
        }
        funcionarioRepository.saveAndFlush(funcionario);
        ModelAndView mv = new ModelAndView("/administrative/funcionarios/cadastro");
        mv.addObject("funcionario", new Funcionario());
        mv.addObject("successMessage", "Funcionário cadastrado com sucesso!");
        return mv;
    }


    @GetMapping("/administrative/funcionarios/detalhes/{id}")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("administrative/funcionarios/show");
        funcionarioRepository.findById(id).ifPresentOrElse(funcionario -> {
            mv.addObject("funcionario", funcionario);
            mv.addObject("listaCidades", cidadeRepository.findAll());
            mv.addObject("listaCargos", cargoRepository.findAll());
            mv.addObject("listaEstados", estadoRepository.findAll());
        }, () -> mv.setViewName("redirect:/administrative/funcionarios/listar"));
        return mv;
    }

    // Atualização dos Dados Pessoais
    @PostMapping("/administrative/funcionarios/atualizarDadosPessoais")
    public ModelAndView atualizarDadosPessoais(@Validated @ModelAttribute Funcionario funcionarioAtualizado, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("administrative/funcionarios/show", "funcionario", funcionarioAtualizado);
        }
        Funcionario funcionarioExistente = funcionarioRepository.findById(funcionarioAtualizado.getId())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado com ID: " + funcionarioAtualizado.getId()));
        funcionarioExistente.setNome(funcionarioAtualizado.getNome());
        funcionarioExistente.setEmail(funcionarioAtualizado.getEmail());
        if (funcionarioAtualizado.getSenha() != null && !funcionarioAtualizado.getSenha().isEmpty() && !passwordEncoder.matches(funcionarioAtualizado.getSenha(), funcionarioExistente.getSenha())) {
            String senhaCodificada = passwordEncoder.encode(funcionarioAtualizado.getSenha());
            funcionarioExistente.setSenha(senhaCodificada);
        }
        funcionarioRepository.save(funcionarioExistente);
        return new ModelAndView("redirect:/administrative/funcionarios/detalhes/" + funcionarioExistente.getId());
    }


    @PostMapping("/administrative/funcionarios/atualizarEndereco")
    public ModelAndView atualizarEndereco(@Validated @ModelAttribute Funcionario funcionarioAtualizado, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("administrative/funcionarios/show", "funcionario", funcionarioAtualizado);
        }
        Funcionario funcionarioExistente = funcionarioRepository.findById(funcionarioAtualizado.getId())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado com ID: " + funcionarioAtualizado.getId()));
        Long cidadeId = funcionarioAtualizado.getEndereco().getCidade() != null ? funcionarioAtualizado.getEndereco().getCidade().getId() : null;
        if (cidadeId == null) {
            throw new IllegalArgumentException("ID da Cidade não pode ser nulo");
        }
        Cidade cidade = cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new IllegalArgumentException("Cidade não encontrada"));
        String novoCep = funcionarioAtualizado.getEndereco().getCep().replaceAll("\\D", "");
        if (funcionarioExistente.getEndereco() == null) {
            funcionarioExistente.setEndereco(new Endereco());
        }
        Endereco enderecoAtualizado = funcionarioExistente.getEndereco();
        enderecoAtualizado.setCep(novoCep);
        enderecoAtualizado.setNumero(funcionarioAtualizado.getEndereco().getNumero());
        enderecoAtualizado.setLogradouro(funcionarioAtualizado.getEndereco().getLogradouro());
        enderecoAtualizado.setBairro(funcionarioAtualizado.getEndereco().getBairro());
        enderecoAtualizado.setComplemento(funcionarioAtualizado.getEndereco().getComplemento());
        enderecoAtualizado.setCidade(cidade);
        funcionarioRepository.save(funcionarioExistente);
        return new ModelAndView("redirect:/administrative/funcionarios/detalhes/" + funcionarioExistente.getId());
    }

    @PostMapping("/administrative/funcionarios/atualizarDadosEmpregaticios")
    public ModelAndView atualizarDadosEmpregaticios(@Validated @ModelAttribute Funcionario funcionarioAtualizado, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("administrative/funcionarios/show", "funcionario", funcionarioAtualizado);
        }
        Funcionario funcionarioExistente = funcionarioRepository.findById(funcionarioAtualizado.getId())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado com ID: " + funcionarioAtualizado.getId()));

        funcionarioExistente.setCargo(funcionarioAtualizado.getCargo());
        funcionarioRepository.save(funcionarioExistente);
        return new ModelAndView("redirect:/administrative/funcionarios/detalhes/" + funcionarioExistente.getId());
    }
}



