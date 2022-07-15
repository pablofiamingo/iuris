package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.*;
import com.proyectoIuris.iuris.repository.UsuariosRepository;
import com.proyectoIuris.iuris.service.Interfaces.*;
import com.proyectoIuris.iuris.service.implementacion.EmailSenderService;
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
    private ICasoService casoService;
    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private EmailSenderService senderService;


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

    @GetMapping("/recuperar")
    public String getRecuperar(Model model, HttpSession session) {

        Util.mostrarAlertas(model, session, "recuperarClave");
        return "recuperarClave";
    }

    @GetMapping("/lista")
    public String getUsuarios(HttpSession session, Model model) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuarioActivo = (Usuario) session.getAttribute("user");

        if (!usuarioActivo.getRol().equals("admin")) return "redirect:/error";

        model.addAttribute("resultados", usuarioService.list());
        return "resultadosUsuario";
    }

    @GetMapping("/login")
    String getLogin(Model model, HttpSession httpSession) {
        //si hay usuario activo, redirige a inicio
        //si no hay usuario logueado, envia un objeto usuario en blanco para que le coloquen los datos de inicio de sesion
        Usuario usuarioActivo = (Usuario) httpSession.getAttribute("user");
        if(usuarioActivo != null) return "redirect:/inicio";

        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @GetMapping("/editar/{idUsuario}")
    public String getEditarUsuario(HttpSession session, Model model, @PathVariable("idUsuario")int idUsuarioAEditar) {
        /*Busca el usuario a editar por su id, obtiene el objeto y lo envia al template para la manipulacion de sus datos */
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuarioActivo = (Usuario) session.getAttribute("user");
        if (!usuarioActivo.getRol().equals("admin")) return "redirect:/inicio";

        Usuario usuarioAEditar = usuarioService.findByIdUsuario(idUsuarioAEditar);
        model.addAttribute("usuario", usuarioAEditar);
        return "editarUsuario";
    }

    @PostMapping("/olvide/clave")
    public String sendMail(@RequestParam(value = "email") String email,Model model, HttpSession session) {

        String estado = "error";
       if(usuariosRepository.findByEmail(email) == null) {
           estado = "error";
       }else {

           //Recibimos el email del form y encontramos al usuario con ese email
           Usuario usuarioMail = usuariosRepository.findByEmail(email);

           //se envia el mail
           senderService.sendEmail(email,
                   "Reenvio de clave",
                   "Hola: " + usuarioMail.getUser() +
                           "\nSu clave es: "
                           + usuarioMail.getPass() +
                           "\nQue tenga un buen día!");
           estado = "exito";
       }

       session.setAttribute("recuperarClave", estado);
       return "redirect:/usuario/recuperar";
    }

    @PostMapping("/registro")
    public String insertUser(@Validated Usuario usuario, Model model) {

        String estado = "error";

        if(usuario.getRol().equals("abogado") || usuario.getRol().equals("empleado")) {
            if(Util.containsIllegals(usuario.getUser()) || Util.containsIllegals(usuario.getFullName())) {
                //Si el usuario a registrar no contiene datos validos se detiene la ejecucion del registro y se notifica el error al usuario
                estado = "error";
            } else {
                /* si esta todo en orden, se crea el usuario */
                usuarioService.insert(usuario);
                estado = "exito";
            }
        }

        model.addAttribute(estado, "true");
        return "agregarUsuario";
    }


    @PostMapping("/editar")
    public String editarUsuario(HttpSession session, Model model, @Validated Usuario usuario) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuarioActivo = (Usuario) session.getAttribute("user");
        if (!usuarioActivo.getRol().equals("admin")) return "redirect:/inicio";

        /*Al editar el usuario, en caso de que se haya cambiado el nombre del mismo, este se actualiza en los casos que posea el usuario, si el mismo es abogado. */
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
        return "editarUsuario";
    }

    @PostMapping("/login")
    public String login(@Validated Usuario u, Model model, HttpSession httpSession) {
        /*Para el login, se busca que exista el usuario buscandolo por su nombre de usuario, luego se comparan las contraseñas*/
        Usuario usuario = usuarioService.findByUsername(u.getUser());

        if(usuario.getPass().equals(u.getPass())) {
            httpSession.setAttribute("user", usuario);
            return "redirect:/inicio";
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