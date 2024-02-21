package com.dev.sig.control;



import com.dev.sig.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.dev.sig.models.Cargo;

import java.util.Optional;


@Controller
public class CargoControl {

    @Autowired
    private CargoRepository cargoRepository;




    @GetMapping("/administrative/cargos/cadastrar")
    public ModelAndView cadastrar(Cargo cargo) {
        ModelAndView mv = new ModelAndView("administrative/cargos/cadastro");
        mv.addObject("cargo", cargo);
        mv.addObject("niveisAcesso", Cargo.NivelAcesso.values()); // Inclui os níveis de acesso no modelo
        return mv;
    }


    @GetMapping("/administrative/cargos/listar")
    public ModelAndView listar() {
        ModelAndView mv=new ModelAndView("administrative/cargos/lista");
        mv.addObject("listaCargos", cargoRepository.findAll());
        mv.addObject("niveisAcesso", Cargo.NivelAcesso.values());
        return mv;
    }

    @GetMapping("/administrative/cargos/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Cargo> cargo = cargoRepository.findById(id);
        ModelAndView mv = cadastrar(cargo.get());
        mv.addObject("niveisAcesso", Cargo.NivelAcesso.values());
        return mv;
    }


    @GetMapping("/administrative/cargos/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Cargo> cargo = cargoRepository.findById(id);
        cargoRepository.delete(cargo.get());
        return listar();
    }

    @PostMapping("/administrative/cargos/salvar")
    public ModelAndView salvar(@Validated Cargo cargo, BindingResult result) {
        if(result.hasErrors()) {
            return cadastrar(cargo);
        }
        cargoRepository.saveAndFlush(cargo);

        return cadastrar(new Cargo());
    }

}