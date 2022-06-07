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

    /**
     * Cuando se le pega a este método por get, primero verifica si existe un usuario en la sesión, si existe lo carga en el Model y lo envia
     * al inicio, para obtener datos como el nombre completo y el rol
     * @param session obtiene la sesión activa
     * @param model modelo por el cual envia luego el usuario activo
     * @return la vista index
     */
    @GetMapping("/inicio")
    public String index(HttpSession session, Model model) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuario = (Usuario) session.getAttribute("user");
        model.addAttribute("usuario", usuario);

        List<DetalleTarea> tareas = listaDeTareasService.getTareas(usuario.getListaDeTareas().getIdToDo());

        if(!tareas.isEmpty()) {
            model.addAttribute("tareas", tareas);
        }

        return "index";
    }

    /**
     * Método buscar accedido por POST, recibe un valor y una indicacion de donde debe buscar dicho valor.
     * El primer condicional verifica que no se hayan ingresado carácteres inválidos, de ser asi, retorna un error.
     * El segundo condicional se encarga de buscar el caracter o la palabra indicada, evaluando si el tipo es caso o cliente.
     * Una vez que ingresa en una de las dos rutas del condicional, crea una lista con los datos solicitados.
     * Si se ingresó un caracter o palabra, se devolvera una lista de todos los resultados que lo contengan, en la ubicación que sea.
     *
     * @param keyword String not null, es el valor que debe buscar
     * @param donde String que indica desde el index si es caso o cliente
     * @param model el modelo por donde se van a pasar los datos hacia la vista
     * @return devuelve index si hay un error, o una vista con los resultados, de haberlos.
     */
    @PostMapping("/buscar")
    public String buscar(@RequestParam(value="valorABuscar") @NotNull String keyword,
                         @RequestParam(value="donde")String donde,
                         Model model) {

        if (Util.containsIllegals(keyword)) {
            model.addAttribute("error", "true");
            return "redirect:/inicio";
        }

        if(donde.equals("cliente") ) {

            List<Cliente> clientes = (List<Cliente>) clienteService.findByNombreOApellido(keyword);
            if(clientes.isEmpty()) return "testClientes";
            model.addAttribute("resultados", clientes);
            model.addAttribute("type", "cliente");

        } else if (donde.equals("caso") ) {

            List<Caso> casos = casoService.buscador(keyword);
            if(casos.isEmpty()) return "testClientes";
            model.addAttribute("resultados", casos);
            model.addAttribute("type", "caso");

        }

        return "testClientes";
    }

}