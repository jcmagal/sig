package com.dev.sig.control;


import com.dev.sig.models.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dev.sig.models.Cidade;
import com.dev.sig.repository.EstadoRepository;
import com.dev.sig.repository.CidadeRepository;

import java.util.Optional;



@Controller
public class CidadeControl {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;


    @GetMapping("/administrative/cidades/cadastrar")
    public ModelAndView cadastrar(Cidade cidade) {
        ModelAndView mv =  new ModelAndView("administrative/cidades/cadastro");
        mv.addObject("cidade",cidade);
        mv.addObject("listaEstados", estadoRepository.findAll());
        return mv;
    }

    @GetMapping("/administrative/cidades/listar")
    public ModelAndView listar() {
        ModelAndView mv=new ModelAndView("administrative/cidades/lista");
        mv.addObject("listaCidades", cidadeRepository.findAll());
        return mv;
    }

    @GetMapping("/administrative/cidades/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Cidade> cidade = cidadeRepository.findById(id);
        return cadastrar(cidade.get());
    }

    @GetMapping("/administrative/cidades/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Cidade> cidade = cidadeRepository.findById(id);
        cidadeRepository.delete(cidade.get());
        return listar();
    }

    @PostMapping("/administrative/cidades/salvar")
    public ModelAndView salvar(@Validated Cidade cidade, BindingResult result) {

        if(result.hasErrors()) {
            return cadastrar(cidade);
        }
        cidadeRepository.saveAndFlush(cidade);

        return cadastrar(new Cidade());
    }

    @GetMapping("/administrative/cidades/{cidadeId}/estado")
    @ResponseBody
    public Estado getEstadoPorCidade(@PathVariable Long cidadeId) {
        // Lógica para buscar o estado baseado no ID da cidade
        return cidadeRepository.findById(cidadeId).map(Cidade::getEstado).orElse(null);
    }

}