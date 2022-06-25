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
        tarea.setEnabled(false);
        tarea.setListaDeTareas(usuario.getListaDeTareas());

        listaDeTareasService.guardarTarea(tarea);

        return "redirect:/inicio";

    }

    @PostMapping("/check")
    public String tacharTarea(@RequestParam("id")int id,
                              @RequestParam("check")boolean check) {
        DetalleTarea tarea = listaDeTareasService.getTarea(id);
        if(check) {
            tarea.setEnabled(false);
        } else {
            tarea.setEnabled(true);
        }
        listaDeTareasService.guardarTarea(tarea);
        return "redirect:/inicio";
    }

    @PostMapping("/eliminar")
    public String eliminarTarea(@RequestParam("idTarea") int idTarea) {
        listaDeTareasService.deleteTarea(idTarea);
        return "redirect:/inicio";
    }
}
