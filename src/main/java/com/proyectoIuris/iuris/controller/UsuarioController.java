package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.service.IUsuarioService;
import com.proyectoIuris.iuris.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    //Métodos por GET
    @GetMapping("/registro")
    public String getRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @GetMapping("/login")
    String getLogin(Model model, HttpSession httpSession) {
        Usuario user = (Usuario) httpSession.getAttribute("user");
        if(httpSession.getAttribute("user") != null) {
            model.addAttribute("nombre", user);
            return "inicio";
        }
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    //Métodos por POST
    @PostMapping("/registro")
    public String insertUser(@Validated Usuario u, Model model) {
        if(u != null) {
            if(u.getRol().equals("abogado") || u.getRol().equals("empleado")) {
                if(Util.containsIllegals(u.getUser()) || Util.containsIllegals(u.getFullName())) {
                    model.addAttribute("error", "true");
                    return "register";
                }
                usuarioService.insert(u);
                model.addAttribute("exito","true");
            }
        }
        return "register";
    }

    @PostMapping("/login")
    public String login(@Validated Usuario u, Model model, HttpSession httpSession) {
        Usuario usuario = usuarioService.findByUsername(u.getUser());
        if(usuario != null) {
            if(usuario.getPass().equals(u.getPass())) {
                model.addAttribute("nombre", usuario.getFullName());
                httpSession.setAttribute("user", usuario);
                return "inicio";
            } else {
                model.addAttribute("error", "true");
            }
        } else {
            model.addAttribute("error", "true");
        }
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/usuario/login";
    }
}