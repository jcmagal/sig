package com.dev.sig.control;

import org.springframework.security.access.prepost.PreAuthorize;
import  org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class PrincipalControl {

    @PreAuthorize("hasAuthority('ACESSO_TOTAL')")
    @GetMapping("/administrative")
    public String acessarPrincipal() {
        return "administrative/home";
    }
}