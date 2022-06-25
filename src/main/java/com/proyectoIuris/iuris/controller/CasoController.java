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
@RequestMapping("/caso")
public class CasoController {
    @Autowired
    private ICasoService casoService;
    @Autowired
    private IArchivoService archivoService;
    @Autowired
    private IClienteService clienteService;

    @GetMapping("/lista")
    public String getCasos(Model model, HttpSession session) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario user = (Usuario) session.getAttribute("user");
        List<Caso> casos;
        if(user.getRol().toLowerCase().equals("abogado")) {
            casos = casoService.listPermisoAbogado(user.getIdUsuario());
        } else {
            casos = casoService.list();
        }
        model.addAttribute("resultados", casos);
        return "resultadosCaso";
    }

    @GetMapping("/lista/{cliente}")
    public String getCasosCliente(Model model,
                                  HttpSession session,
                                  @PathVariable("cliente") int idCliente) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario user = (Usuario) session.getAttribute("user");
        List<Caso> casos = casoService.findCasoByIdCliente(idCliente);
        model.addAttribute("resultados", casos);
        return "resultadosCaso";
    }

    @GetMapping("/alta")
    public String getAgregarCaso(Caso caso, Model model, HttpSession session) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";

        Usuario user = (Usuario) session.getAttribute("user");
        List<Cliente> clientes;
        if(user.getRol().toLowerCase().equals("abogado")) {
            clientes = clienteService.listPermisoAbogado(user.getIdUsuario());
        } else {
            clientes = clienteService.list();
        }
        caso.setRepresentante(user.getFullName());

        String redirected = (String) session.getAttribute("agregarCaso");
        if(redirected!=null) {
            model.addAttribute(redirected,true);
            session.removeAttribute("agregarCaso");
        }
        model.addAttribute("caso", caso);
        model.addAttribute("user", user);
        model.addAttribute("clientes", clientes);
        return "agregarCaso";
    }

    @GetMapping("/editar/{idCaso}")
    public String getEditarCaso(@PathVariable("idCaso") int idCaso,
                                Model model,
                                HttpSession session ) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";

        String redirected = (String) session.getAttribute("editarCaso");
        if(redirected!=null) {
            model.addAttribute(redirected,true);
            session.removeAttribute("editarCaso");
        }

        Caso caso = casoService.findCasoById(idCaso);
        model.addAttribute("caso", caso);
        return "editarCaso";
    }

    //POSTMAPPING-----------------------------------------------------------------------------------------------------
    @PostMapping("/agregar")
    public String agregarCaso(@Validated Caso caso,
                              Model model,
                              @RequestParam("cliente")int id,
                              HttpSession session) {
        String estado = "";
        if (caso!=null) {
            Cliente cli = clienteService.findById(id);
            caso.setCliente(cli);
            casoService.save(caso);
            session.setAttribute("agregarCaso","exito");
        } else {
            session.setAttribute("agregarCaso","error");
        }
        return "redirect:/caso/alta";
    }

    @PostMapping("/editar")
    public String editarCaso(@Validated Caso caso, Model model, HttpSession session) {
        if (caso!=null) {
            if(casoService.save(caso)){
                session.setAttribute("editarCaso","exito");
            } else {
                session.setAttribute("editarCaso","error");
            }
        } else {
            session.setAttribute("editarCaso","error");
        }
        return "redirect:/caso/editar/" + caso.getIdCaso();
    }

    @PostMapping("/baja")
    public String eliminarCaso(@RequestParam("id") int idCaso,
                               Model model) {
        if(casoService.delete(idCaso)) {
            model.addAttribute("baja", "exito");
        } else {
            model.addAttribute("baja", "error");
        }
        return "redirect:/caso/lista";
    }

}
