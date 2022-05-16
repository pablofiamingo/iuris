package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.Caso;
import com.proyectoIuris.iuris.model.ListaDeTareas;
import com.proyectoIuris.iuris.model.Pago;
import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.service.IArchivoService;
import com.proyectoIuris.iuris.service.IListaDeTareasService;
import com.proyectoIuris.iuris.service.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/listaDeTareas")
public class ListaDeTareasController {

    @Autowired
    private IListaDeTareasService listaDeTareasService;

    @Autowired
    private IArchivoService fileService;

    @GetMapping("/lista")
    public String getAll(Model model) {
        List<ListaDeTareas> listaDeTareas = listaDeTareasService.getAll();
        model.addAttribute("listaDeTareas", listaDeTareas);
        return "listadoDeTareas";
    }

    @GetMapping("/agregar")
    public String getAgregarTarea(ListaDeTareas tarea, Model model, HttpSession httpSession) {
        Usuario user = (Usuario) httpSession.getAttribute("user");
        tarea.setTarea(user.getFullName());
        model.addAttribute("tarea", tarea);
        model.addAttribute("user", user);
        return "agregarTarea";
    }

    @GetMapping(value = "/editar/{idToDo}")
    public String getEditarTarea(@PathVariable("idToDo") int idToDo, Model model ) {
        Optional<ListaDeTareas> tarea = listaDeTareasService.findById(idToDo);
        model.addAttribute("idToDo", idToDo);
        return "editarTarea";
    }

    @GetMapping("/ver/{idToDo}")
    public String getPagoByID(@PathVariable("idToDo") int idToDo, Model model) {
        if(listaDeTareasService.findById(idToDo).isPresent()) {
            Optional<ListaDeTareas> tarea = listaDeTareasService.findById(idToDo);
            model.addAttribute("tarea", tarea);
        } else {
            model.addAttribute("error", "No se encontró ninguna tarea.");
        }
        return "vistaTarea";
    }
    //POST

    @PostMapping("/alta")
    public String agregarTarea(@RequestPart ListaDeTareas tarea, HttpSession session) {
        if (listaDeTareasService.save(tarea)) {
            fileService.crearDir(System.getenv("APPDATA") +
                    "\\IURIS\\Archivos\\Pagos\\"
                    + tarea.getIdToDo());
        }
        return "altaTarea";
    }

    @PostMapping("/editar")
    public String editarTarea(@Validated ListaDeTareas tarea, Model model) {
        if (tarea!=null) {
            listaDeTareasService.save(tarea);
            model.addAttribute("exito", "tarea editada.");
            return "inicio";
        } else return "agregarTarea";
    }

    @PostMapping("/eliminar")
    public void eliminarTarea(@RequestParam("idToDo") int idToDo) {
        listaDeTareasService.delete(idToDo);
    }
}
