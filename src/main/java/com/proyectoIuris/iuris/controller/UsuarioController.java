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
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    //Métodos por GET
    @GetMapping("/registro")
    public String getRegistro(Model model) {
        //redirect a registro enviandole un Usuario para que manipule el form
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @GetMapping("/login")
    String getLogin(Model model) {
        //redirect a login enviandole un Usuario para que manipule el form
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    //Métodos por POST
    @PostMapping("/registro")
    public String insertUser(@Validated Usuario u, Model model) {
        //si el usuario no es null, y el rol es empleado o abogado, permite el registro
        //las otras validaciones ya se hacen con el required de html
        if(u != null) {
            if(u.getRol().equals("abogado") || u.getRol().equals("empleado")) {
                usuarioService.insert(u);
                model.addAttribute("exito","Usuario registrado con éxito.");
            }
        }
        return "register";
        //falta agregar mas seguridad para evitar caracteres raros, etc.
    }

    @PostMapping("/login")
    public String login(@Validated Usuario u, Model model) {
        String error = "Los datos ingresados son incorrectos. Por favor, inténtelo nuevamente.";
        //Busca el usuario con el username indicado
        Usuario usuario = usuarioService.findByUsername(u.getUser());

        //si encuentra el usuario (o sea, no es null) compara la contraseña ingresada con la registrada
        if(usuario != null) {
            if(usuario.getPass().equals(u.getPass())) {
                model.addAttribute("nombre", usuario.getFullName());
                return "inicio";
            } else {
                model.addAttribute("error", error);
                return "login";
            }
        } else {
            model.addAttribute("error", error);
            return "login";
        }
    }
}
