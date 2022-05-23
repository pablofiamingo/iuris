package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.DetalleTarea;
import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.service.Interfaces.IListaDeTareasService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/listaDeTareas")
public class ListaDeTareasController {

    @Autowired
    private IListaDeTareasService listaDeTareasService;

    //POST
    @PostMapping("/agregar")
    public String agregarTarea(@NotNull @RequestParam("tareaNueva") String tareaNueva,
                               HttpSession session,
                               Model model) {

        Usuario usuario = (Usuario) session.getAttribute("user");

        DetalleTarea tarea = new DetalleTarea();
        tarea.setTarea(tareaNueva);
        tarea.setEnabled(true);
        tarea.setListaDeTareas(usuario.getListaDeTareas());

        listaDeTareasService.guardarTarea(tarea);

        //redirige al controller, no a la vista
        return "redirect:/inicio";

    }

    /*@PostMapping("/editar")
    public String editarTarea(@RequestParam("textohtml") String texto,
                              @RequestParam("idTarea")int idTarea,
                              Model model) {

        if (tarea!=null) {
            listaDeTareasService.save(tarea);
            model.addAttribute("exito", "tarea editada.");
            return "inicio";
        } else return "agregarTarea";
    }*/

    @PostMapping("/eliminar")
    public String eliminarTarea(@RequestParam("idTarea") int idTarea) {
        /*
        * @RequestParam("nombre que le pusiste en el html") tipodedato nombre de la variable
        * y esta variable es para usar el dato que trajiste desde el html
        * en el metodo del controlador
        * */
        listaDeTareasService.deleteTarea(idTarea);
        return "redirect:/inicio";
    }
}
