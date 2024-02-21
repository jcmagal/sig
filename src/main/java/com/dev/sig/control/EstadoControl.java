package com.dev.sig.control;



import com.dev.sig.models.Cidade;
import com.dev.sig.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.dev.sig.models.Estado;
import com.dev.sig.repository.CidadeRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class EstadoControl {

    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;


    @GetMapping("/administrative/estados/cadastrar")
    public ModelAndView cadastrar(Estado estado) {
        ModelAndView mv =  new ModelAndView("administrative/estados/cadastro");
        mv.addObject("estado",estado);
        return mv;
    }

    @GetMapping("/administrative/estados/listar")
    public ModelAndView listar() {
        ModelAndView mv=new ModelAndView("administrative/estados/lista");
        mv.addObject("listaEstados", estadoRepository.findAll());
        return mv;
    }

    @GetMapping("/administrative/estados/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Estado> estado = estadoRepository.findById(id);
        return cadastrar(estado.get());
    }

    @GetMapping("/administrative/estados/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Estado> estado = estadoRepository.findById(id);
        estadoRepository.delete(estado.get());
        return listar();
    }

    @PostMapping("/administrative/estados/salvar")
    public ModelAndView salvar(@Validated Estado estado, BindingResult result) {
        if(result.hasErrors()) {
            return cadastrar(estado);
        }
        estadoRepository.saveAndFlush(estado);

        return cadastrar(new Estado());
    }

    @GetMapping("/administrative/estados/{estadoId}/cidades")
    @ResponseBody
    public List<Cidade> getCidadesPorEstado(@PathVariable Long estadoId) {
        return cidadeRepository.findByEstadoId(estadoId);
    }
}