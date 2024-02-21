package com.dev.sig.control;

import com.dev.sig.models.Loja;
import com.dev.sig.repository.CidadeRepository;
import com.dev.sig.repository.LojaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class LojaControl {

    @Autowired
    private LojaRepository lojaRepository;
    @Autowired
    private CidadeRepository cidadeRepository;



    @GetMapping("/administrative/lojas/cadastrar")
    public ModelAndView cadastrar(Loja loja){
        ModelAndView mv = new ModelAndView("administrative/lojas/cadastro");
        mv.addObject("lojas", loja);
        mv.addObject("listaCidades", cidadeRepository.findAll());
        return mv;
    }

    @GetMapping("/administrative/lojas/listar")
    public ModelAndView listar(){
        ModelAndView mv=new ModelAndView("administrative/lojas/lista");
        mv.addObject("listaLojas", lojaRepository.findAll());
        return mv;
    }

    @GetMapping("/administrative/lojas/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Loja> loja = lojaRepository.findById(id);
        return cadastrar(loja.get());
    }

    @GetMapping("/administrative/lojas/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id){
        Optional<Loja> loja = lojaRepository.findById(id);
        lojaRepository.delete(loja.get());
        return listar();
    }

    @PostMapping("/administrative/lojas/salvar")
    public ModelAndView salvar(@Validated Loja loja, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(loja);
        }
        lojaRepository.saveAndFlush(loja);
        ModelAndView mv = new ModelAndView("/administrative/lojas/cadastro");
        mv.addObject("lojas", new Loja());
        return mv;
    }


}