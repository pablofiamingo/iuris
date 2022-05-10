package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class TestUsuarioController {

    @Autowired
    private IUsuarioService uService;

    @GetMapping("/register")
    public String getRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/insertUser")
    public String insertUser(@Validated Usuario u, Model model) {
        uService.insert(u);
        return "register";

    }

    @GetMapping("/login")
    String getLogin() {
        return "login";
    }
}
