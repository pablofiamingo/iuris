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

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService uService;

    //Métodos por GET
    @GetMapping("/registro")
    public String getRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @GetMapping("/login")
    String getLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    //Métodos por POST
    @PostMapping("/registro")
    public String insertUser(@Validated Usuario u, Model model) {
        uService.insert(u);
        return "register";

    }

    @PostMapping("/login")
    public String login(@Validated Usuario u, Model model) {

        Usuario usuario = null;
        boolean isRegistrado = false;
        List<Usuario> usuarios = uService.list();

        for (Usuario user : usuarios) {
            if(user.getUser().equals(u.getUser()) && user.getPass().equals(u.getPass())) {
                usuario = user;
                isRegistrado = true;

                //agregar el  tema de los roles
            }
        }

        if (isRegistrado) {
            model.addAttribute("nombre", usuario.getFullName());
            return "inicio";
        } else return "register";
    }
}
