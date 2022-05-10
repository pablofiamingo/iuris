package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.Archivo;
import com.proyectoIuris.iuris.model.Caso;
import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.service.IArchivoService;
import com.proyectoIuris.iuris.service.ICasoService;
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

    @GetMapping("/lista")
    public String getCasos(Model model) {
        List<Caso> casos = casoService.list(1);
        model.addAttribute("listaDeCasos", casos);
        return "listadoCasos";
    }

    @GetMapping("/agregar")
    public String getAgregarCaso(Caso caso, Model model, HttpSession httpSession) {
        Usuario user = (Usuario) httpSession.getAttribute("user");
        caso.setRepresentante(user.getFullName());
        model.addAttribute("caso", caso);
        model.addAttribute("user", user);
        return "agregarCaso";
    }

    @GetMapping(value = "/editar/{idCaso}")
    public String getEditarCaso(@PathVariable("idCaso") int idCaso, Model model ) {
        Caso caso = casoService.findCasoById(idCaso);
        model.addAttribute("caso", caso);
        return "editarCaso";
    }

    @GetMapping("/ver/{idCaso}")
    public String getCaso(@PathVariable("idCaso") int idCaso, Model model) {
        Caso caso = casoService.findCasoById(idCaso);
        List<Archivo> archivos = caso.getArchivos();
        model.addAttribute("caso", caso);
        model.addAttribute("archivos", archivos);

        return "";
    }

    //POSTMAPPING-----------------------------------------------------------------------------------------------------
    @PostMapping("/agregar")
    public String agregarCaso(@Validated Caso caso, Model model) {
        if (caso!=null) {
            casoService.save(caso);
            model.addAttribute("exito", "Caso agregado con éxito.");
            return "agregarCaso";
        } else return "agregarCaso";
    }

    @PostMapping("/editar")
    public String editarCaso(@Validated Caso caso, Model model) {
        if (caso!=null) {
            casoService.save(caso);
            model.addAttribute("exito", "Caso editado con éxito.");
            return "inicio";
        } else return "agregarCaso";
    }

    @PostMapping("/eliminar")
    public void eliminarCaso(@RequestParam("idCaso") int idCaso) {
        casoService.delete(idCaso);
    }

}
