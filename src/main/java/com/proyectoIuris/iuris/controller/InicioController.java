package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.*;
import com.proyectoIuris.iuris.service.Interfaces.*;
import com.proyectoIuris.iuris.util.Util;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/")
public class InicioController {
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private ICasoService casoService;
    @Autowired
    private IListaDeTareasService listaDeTareasService;
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private ICalendarioService calendarioService;

    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/inicio";
    }

    @GetMapping("/inicio")
    public String index(HttpSession session, Model model) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuario = (Usuario) session.getAttribute("user");

        if(usuario.getCalendario() == null) {
            Calendario calendario = new Calendario();
            calendario.setUsuario(usuario);
            calendarioService.save(calendario);
            usuario.setCalendario(calendario);
            usuarioService.insert(usuario);
        }
        if (usuario.getListaDeTareas() == null) {
            ListaDeTareas lista = new ListaDeTareas();
            lista.setUsuario(usuario);
            listaDeTareasService.save(lista);
            usuario.setListaDeTareas(lista);
            usuarioService.insert(usuario);
        }

        List<DetalleTarea> tareas = listaDeTareasService.getTareas(usuario.getListaDeTareas().getIdToDo());

        if(!tareas.isEmpty()) model.addAttribute("tareas", tareas);
        model.addAttribute("usuario", usuario);
        return "index";
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam(value="valorABuscar") @NotNull String keyword, @RequestParam(value="donde")String donde, Model model, HttpSession session) {

        Usuario user = (Usuario) session.getAttribute("user");

        if (Util.containsIllegals(keyword)) {
            model.addAttribute("error", "true");
            return "redirect:/inicio";
        }

        if(donde.equals("cliente") ) {

            List<Cliente> clientes;
            if (user.getRol().equalsIgnoreCase("abogado")) {
                clientes = clienteService.buscadorPermisoAbogado(keyword, user.getIdUsuario());
            } else {
                clientes = clienteService.buscadorGeneral(keyword);
            }

            model.addAttribute("resultados", clientes);
            return "resultadosCliente";

        } else if (donde.equals("caso") ) {

            List<Caso> casos;
            if (user.getRol().equalsIgnoreCase("abogado")) {
                casos = casoService.buscadorPermisoAbogado(keyword, user.getIdUsuario());
            } else {
                casos = casoService.buscadorGeneral(keyword);
            }

            model.addAttribute("resultados", casos);
            return "resultadosCaso";
        }

        return "redirect:/inicio";
    }

}