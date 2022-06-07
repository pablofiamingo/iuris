package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.Calendario;
import com.proyectoIuris.iuris.model.ListaDeTareas;
import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.service.Interfaces.ICalendarioService;
import com.proyectoIuris.iuris.service.Interfaces.IListaDeTareasService;
import com.proyectoIuris.iuris.service.Interfaces.IUsuarioService;
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
    @Autowired
    private IListaDeTareasService listaDeTareasService;
    @Autowired
    private ICalendarioService calendarioService;

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
    public String insertUser(@Validated Usuario usuario,
                             Model model,
                             HttpSession session) {
        //para registrar, me fijo si es admin primero
        //parece al cuete la validacion pero por las dudas la dejo
        Usuario usuarioActivo = (Usuario) session.getAttribute("user");
        if (usuarioActivo.getRol() != "admin") {
            return "redirect:/inicio";
        }
        //después paso a registrar al usuario
        if(usuario != null) {
            if(usuario.getRol().equals("abogado") || usuario.getRol().equals("empleado")) {
                if(Util.containsIllegals(usuario.getUser()) || Util.containsIllegals(usuario.getFullName())) {
                    model.addAttribute("error", "true");
                    return "register";
                }
                Usuario usuarioInsertado = usuarioService.insert(usuario); //aca el metodo save del repository te devuelve la entidad insertada, segun la documentacion
                //por lo tanto, el return lo guardo en usuarioInsertado para tener el usuario pero con el id que se le puso en al bd
                //para poder poner este valor en la lista de tareas
                ListaDeTareas listaDeTareas = new ListaDeTareas();
                listaDeTareas.setUsuario(usuarioInsertado);
                usuarioInsertado.setListaDeTareas(listaDeTareas);
                listaDeTareasService.save(listaDeTareas);

                Calendario calendario = new Calendario();
                calendario.setUsuario(usuarioInsertado);
                usuarioInsertado.setCalendario(calendario);
                calendarioService.save(calendario);

                usuarioService.insert(usuarioInsertado);

                model.addAttribute("exito","true");
            }
            model.addAttribute("error", "true");
        }
        return "register";
    }

    @PostMapping("/login")
    public String login(@Validated Usuario u, Model model, HttpSession httpSession) {
        Usuario usuario = usuarioService.findByUsername(u.getUser());
        if(usuario != null) {
            if(usuario.getPass().equals(u.getPass())) {
                httpSession.setAttribute("user", usuario);
                return "redirect:/inicio";
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