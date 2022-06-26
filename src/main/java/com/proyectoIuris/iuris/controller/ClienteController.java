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
    @GetMapping("/alta")
    public String getAltaCliente(Model model,
                                 HttpSession session) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuario = (Usuario) session.getAttribute("user");

        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);

        model.addAttribute("cliente", cliente);
        return "agregarCliente";
    }

    @GetMapping("/editar/{id}")
    public String getEditarCliente(Model model,
                                 HttpSession session,
                                 @PathVariable("id") int id) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuario = (Usuario) session.getAttribute("user");

        Cliente cliente = clienteService.findById(id);

        model.addAttribute("cliente", cliente);
        return "editarCliente";
    }

    @GetMapping("/lista")
    public String getListaClientes(HttpSession session,
                                   Model model) {
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuario = (Usuario) session.getAttribute("user");
        List<Cliente>clientes;
        if(usuario.getRol().toLowerCase().equals("abogado")){
            clientes = clienteService.listPermisoAbogado(usuario.getIdUsuario());
        } else {
            clientes = clienteService.list();
        }
        model.addAttribute("resultados", clientes);
        return "resultadosCliente";
    }

    //POST MAPPING-----------------------------------------------------------------------------------------------
    @PostMapping("/alta")
    public String agregarCliente(@Validated Cliente cliente,
                                 HttpSession session,
                                 Model model) {
        String estado = "";
        if (!Util.isLogged(session)) return "redirect:/usuario/login";

        if (clienteService.findByDni(cliente.getDni()) != null){
            estado = "error";
        } else {
            clienteService.save(cliente);
            fileService.crearDir(System.getenv("APPDATA") + "\\IURIS\\Archivos\\Clientes\\" + cliente.getIdCliente());
            estado = "exito";
        }

        model.addAttribute(estado, true);
        return "agregarCliente";
    }

    @PostMapping("/editar")
    public String editarCliente(@Validated Cliente cliente,
                                HttpSession session,
                                Model model) {
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario user = (Usuario) session.getAttribute("user");
        String estado = "";

        cliente.setUsuario(user);
        if (clienteService.save(cliente)) {
            estado = "exito";
        } else {
            estado = "error";
        }
        model.addAttribute(estado,true);
        return "editarCliente";
    }

}
