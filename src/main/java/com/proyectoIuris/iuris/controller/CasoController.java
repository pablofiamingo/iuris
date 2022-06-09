package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.Archivo;
import com.proyectoIuris.iuris.model.Caso;
import com.proyectoIuris.iuris.model.Cliente;
import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.service.Interfaces.IArchivoService;
import com.proyectoIuris.iuris.service.Interfaces.ICasoService;
import com.proyectoIuris.iuris.service.Interfaces.IClienteService;
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

        List<Caso> casos = casoService.list(user.getIdUsuario());

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
        System.out.println(casos);
        model.addAttribute("resultados", casos);
        return "resultadosCaso";
    }

    @GetMapping("/alta")
    public String getAgregarCaso(Caso caso, Model model, HttpSession session) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";

        Usuario user = (Usuario) session.getAttribute("user");
        List<Cliente> clientes = clienteService.list(user.getIdUsuario());

        caso.setRepresentante(user.getFullName());
        model.addAttribute("caso", caso);
        model.addAttribute("user", user);
        model.addAttribute("clientes", clientes);

        return "agregarCaso";
    }

    @GetMapping(value = "/editar/{idCaso}")
    public String getEditarCaso(@PathVariable("idCaso") int idCaso, Model model, HttpSession session ) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";

        Caso caso = casoService.findCasoById(idCaso);
        model.addAttribute("caso", caso);
        return "editarCaso";
    }

    @GetMapping("/ver/{idCaso}")
    public String getCaso(@PathVariable("idCaso") int idCaso, Model model, HttpSession session) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";

        Caso caso = casoService.findCasoById(idCaso);
        List<Archivo> archivos = caso.getArchivos();
        model.addAttribute("caso", caso);
        model.addAttribute("archivos", archivos);

        return "";
    }

    //POSTMAPPING-----------------------------------------------------------------------------------------------------
    @PostMapping("/agregar")
    public String agregarCaso(@Validated Caso caso,
                              Model model,
                              @RequestParam("cliente")int id) {
        if (caso!=null) {
            Cliente cli = clienteService.findById(id);
            caso.setCliente(cli);
            casoService.save(caso);
            model.addAttribute("exito", "Caso agregado con éxito.");
            return "redirect:/caso/alta";
        } else return "redirect:/caso/alta";
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
