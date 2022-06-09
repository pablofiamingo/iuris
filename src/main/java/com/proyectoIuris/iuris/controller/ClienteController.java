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
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IArchivoService fileService;

    //GET MAPPING-----------------------------------------------------------------------------------------------------
    //este creo que tambien se va baneado por bobi
    @GetMapping("/alta")
    public String getAltaCliente(Model model, HttpSession session) {
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuario = (Usuario) session.getAttribute("user");
        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        model.addAttribute("cliente", cliente);
        return "agregarCliente";
    }

    @GetMapping(value = "/editar/{idCliente}")
    public String getEditarCliente(@PathVariable("idCliente") int idCliente, Model model ) {
        Cliente cliente = clienteService.findById(idCliente);
        model.addAttribute("cliente", cliente);
        return "editarCliente";
    }

    @GetMapping("/ver/{idCliente}")
    public String getClienteByID(@PathVariable("idCliente") int idCliente, Model model) {
        if(clienteService.findById(idCliente) != null) {
            Cliente cliente = clienteService.findById(idCliente);
            model.addAttribute("cliente", cliente);
            return "vistaCliente";
        } else {
            model.addAttribute("error", "No se encontr� el cliente.");
            return "vistaCliente";
        }
    }

    //mepa que este get lo fleto, vemos.
    @GetMapping("/lista")
    public String getListaClientes(HttpSession session, Model model) {
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuario = (Usuario) session.getAttribute("user");
        List<Cliente> clientes = clienteService.list(usuario.getIdUsuario());
        model.addAttribute("clientes", clientes);
        return "listaClientes";
    }

    //POST MAPPING-----------------------------------------------------------------------------------------------

    @PostMapping("/alta")
    public String agregarCliente(@Validated Cliente cliente,
                                 HttpSession session,
                                 Model model) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";

        if(clienteService.findByDni(cliente.getDni()) != null) {
            model.addAttribute("error", "El DNI ingresado ya existe en los registros.");
            System.out.println("El DNI ingresado ya existe en los registros.");
            return "agregarCliente";
        }

        if(clienteService.save(cliente)) {
            fileService.crearDir(System.getenv("APPDATA") + "\\IURIS\\Archivos\\Clientes\\" + cliente.getIdCliente());
            return "redirect:/inicio";
        } else return "agregarCliente";
    }

    @PostMapping("/baja")
    public String eliminarCliente(@RequestParam(value = "id", required = true) int id, Model model) {
       if (clienteService.delete(id)) {
           model.addAttribute("exito", true);
           return "inicio";
       } else {
           model.addAttribute("exito", false);
           return "vistaCliente";
       }
    }

    @PostMapping("/editar")
    public String editarCliente(@RequestPart Cliente cliente, Model model) {
       if (clienteService.save(cliente)) {
           model.addAttribute("exito", true);
           model.addAttribute("cliente", cliente);
           return "vistaCliente";
       } else {
           model.addAttribute("exito", false);
           model.addAttribute("cliente", cliente);
           return "vistaCliente";
       }
    }

}
