package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.*;
import com.proyectoIuris.iuris.service.Interfaces.*;
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
    @Autowired
    private ICasoService casoService;

    //Métodos por GET
    @GetMapping("/registro")
    public String getRegistro(Model model, HttpSession session) {
        //chequeo que esté logueado, si no lo esta va a login, caso contrario obtengo el usuario
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuarioActivo = (Usuario) session.getAttribute("user");
        //si el rol no es admin va al inicio
        String redirect = usuarioActivo.getRol().equals("admin") ? "agregarUsuario" : "redirect:/error";
        //envio un nuevo objeto usuario para que le carguen datos
        model.addAttribute("usuario", new Usuario());
        return redirect;
    }

    @GetMapping("/lista")
    public String getUsuarios(HttpSession session, Model model) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuarioActivo = (Usuario) session.getAttribute("user");
        if (!usuarioActivo.getRol().equals("admin")) return "redirect:/error";

        List<Usuario> usuarios = usuarioService.list();
        model.addAttribute("resultados", usuarios);

        return "resultadosUsuario";
    }

    @GetMapping("/login")
    String getLogin(Model model, HttpSession httpSession) {
        //si hay usuario activo, redirige a inicio
        //si no hay usuario logueado, envia un objeto usuario en blanco para que
        //le coloquen los datos de inicio de sesion
        Usuario usuarioActivo = (Usuario) httpSession.getAttribute("user");
        if(usuarioActivo != null) return "redirect:/inicio";

        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @PostMapping("/registro")
    public String insertUser(@Validated Usuario usuario, Model model) {
        if(usuario != null) {
            if(usuario.getRol().equals("abogado") || usuario.getRol().equals("empleado")) {

                if(Util.containsIllegals(usuario.getUser()) || Util.containsIllegals(usuario.getFullName())) {
                    //Si el usuario a registrar no contiene datos validos
                    //se detiene la ejecucion del registro y se notifica el error
                    //al usuario
                    model.addAttribute("error", "true");
                    return "agregarUsuario";
                }
                /* si esta todo en orden, se crea el usuario
                *     y al mismo se le asigna una lista de tareas y un calendario
                *       para que pueda utilizar correctamente la aplicacion */
                Usuario usuarioInsertado = usuarioService.insert(usuario);

                ListaDeTareas listaDeTareas = new ListaDeTareas();
                listaDeTareas.setUsuario(usuarioInsertado);
                usuarioInsertado.setListaDeTareas(listaDeTareas);
                listaDeTareasService.save(listaDeTareas);

                Calendario calendario = new Calendario();
                calendario.setUsuario(usuarioInsertado);
                usuarioInsertado.setCalendario(calendario);
                calendarioService.save(calendario);

                /* finalmente se guardan tanto el calendario como la lista de tareas
                en el usuario y se actualiza la bbdd */
                usuarioService.insert(usuarioInsertado);

                model.addAttribute("exito","true");
            } else {
                model.addAttribute("error", "true");
            }
        }
        return "agregarUsuario";
    }

    @GetMapping("/editar/{idUsuario}")
    public String getEditarUsuario(HttpSession session, Model model, @PathVariable("idUsuario")int idUsuarioAEditar) {
        /*Busca el usuario a editar por su id, obtiene el objeto
        * y lo envia al template para la manipulacion de sus datos */
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuarioActivo = (Usuario) session.getAttribute("user");
        if (!usuarioActivo.getRol().equals("admin")) return "redirect:/inicio";

        Usuario usuarioAEditar = usuarioService.findByIdUsuario(idUsuarioAEditar);
        model.addAttribute("usuario", usuarioAEditar);
        return "editarUsuario";
    }


    @PostMapping("/editar")
    public String editarUsuario(HttpSession session, Model model, @Validated Usuario usuario) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuarioActivo = (Usuario) session.getAttribute("user");
        if (!usuarioActivo.getRol().equals("admin")) return "redirect:/inicio";

        if(usuario!=null) {
            /*
            * Al editar el usuario, en caso de que se haya cambiado
            * el nombre del mismo, este se actualiza en los casos
            * que posea el usuario, si el mismo es abogado. */
            usuarioService.insert(usuario);

            if(usuario.getRol().equals("abogado")) {
                List<Caso> casos = casoService.listPermisoAbogado(usuario.getIdUsuario());
                if(!casos.isEmpty()) {
                    for (Caso caso : casos) {
                        caso.setRepresentante(usuario.getFullName());
                        casoService.save(caso);
                    }
                }
            }

            model.addAttribute("exito", true);
        } else {
            model.addAttribute("error", true);
        }
        return "editarUsuario";
    }

    @PostMapping("/login")
    public String login(@Validated Usuario u, Model model, HttpSession httpSession) {
        /*Para el login, se busca que exista el usuario buscandolo
        * por su nombre de usuario, luego se comparan las contraseñas*/
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
        //elimina al usuario de la sesion actual
        session.removeAttribute("user");
        return "redirect:/usuario/login";
    }
}