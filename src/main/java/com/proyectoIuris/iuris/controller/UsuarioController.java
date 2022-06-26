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
import java.util.List;

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
    public String getRegistro(Model model,
                              HttpSession session) {
        if (!Util.isLogged(session)) return "redirect:/usuario/login";

        Usuario usuarioActivo = (Usuario) session.getAttribute("user");
        if (!usuarioActivo.getRol().equals("admin")) {
            return "redirect:/inicio";
        }

        model.addAttribute("usuario", new Usuario());
        return "agregarUsuario";
    }

    @GetMapping("/lista")
    public String getUsuarios(HttpSession session, Model model) {
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario user = (Usuario) session.getAttribute("user");
        if (!user.getRol().equals("admin")) {
            return "redirect:/inicio";
        }
        List<Usuario> usuarios = usuarioService.list();
        model.addAttribute("resultados", usuarios);
        return "resultadosUsuario";
    }

    @GetMapping("/login")
    String getLogin(Model model,
                    HttpSession httpSession) {
        Usuario user = (Usuario) httpSession.getAttribute("user");
        if(httpSession.getAttribute("user") != null) {
            model.addAttribute("nombre", user);
            return "redirect:/inicio";
        }
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    //Métodos por POST
    @PostMapping("/registro")
    public String insertUser(@Validated Usuario usuario, Model model, HttpSession session) {
        if(usuario != null) {
            if(usuario.getRol().equals("abogado") || usuario.getRol().equals("empleado")) {
                if(Util.containsIllegals(usuario.getUser()) || Util.containsIllegals(usuario.getFullName())) {
                    model.addAttribute("error", "true");
                    return "agregarUsuario";
                }
                Usuario usuarioInsertado = usuarioService.insert(usuario);

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
            } else {
                model.addAttribute("error", "true");
            }
        }
        return "agregarUsuario";
    }

    @GetMapping("/editar/{idUsuario}")
    public String getEditarUsuario(HttpSession session, Model model, @PathVariable("idUsuario")int idUsuario) {
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario user = (Usuario) session.getAttribute("user");
        if (!user.getRol().equals("admin")) {
            return "redirect:/inicio";
        }
        Usuario usuario = usuarioService.findByIdUsuario(idUsuario);
        model.addAttribute("usuario", usuario);
        return "editarUsuario";
    }


    @PostMapping("/editar")
    public String editarUsuario(HttpSession session, Model model, @Validated Usuario usuario) {
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario user = (Usuario) session.getAttribute("user");
        if (!user.getRol().equals("admin")) {
            return "redirect:/inicio";
        }
        if(usuario!=null) {
            usuarioService.insert(usuario);
            model.addAttribute("exito", true);
        } else {
            model.addAttribute("error", true);
        }
        return "editarUsuario";
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