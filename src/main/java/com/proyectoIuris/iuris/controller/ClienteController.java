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
    @GetMapping("/alta/{id}")
    public String getAltaCliente(Model model,
                                 HttpSession session,
                                 @PathVariable(value = "id", required = false) int id) {
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuario = (Usuario) session.getAttribute("user");

        Integer idObj = id;
        Cliente cliente;

        if (idObj != 0) {
            cliente = clienteService.findById(id);
            model.addAttribute("accion", "editar");
        } else {
            cliente = new Cliente();
            cliente.setUsuario(usuario);
            model.addAttribute("accion", "agregar");
        }

        model.addAttribute("cliente", cliente);
        return "agregarCliente";
    }

    @GetMapping("/lista")
    public String getListaClientes(HttpSession session, Model model) {
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario usuario = (Usuario) session.getAttribute("user");
        List<Cliente> clientes = clienteService.list(usuario.getIdUsuario());
        model.addAttribute("clientes", clientes);
        return "listaClientes";
    }

    //POST MAPPING-----------------------------------------------------------------------------------------------
    /**
     * Este es el supermétodo para agregar y editar
     * @param cliente
     * @param accion
     * @param session
     * @param model
     * @return
     */
    @PostMapping("/alta")
    public String agregarEditarCliente(@Validated Cliente cliente,
                                 @RequestParam("accion") String accion,
                                 HttpSession session,
                                 Model model) {
        String resultado = "", mensaje = "";

        if (!Util.isLogged(session)) return "redirect:/usuario/login";

        switch(accion.toLowerCase()) {
            case "agregar":
                if (clienteService.findByDni(cliente.getDni()) != null){
                    resultado = "error";
                    mensaje = "El DNI ingresado ya existe en los registros.";
                } else {
                    clienteService.save(cliente);
                    fileService.crearDir(System.getenv("APPDATA") + "\\IURIS\\Archivos\\Clientes\\" + cliente.getIdCliente());
                    resultado = "exito";
                    mensaje = "El cliente se agregó con éxito.";
                }
                break;
            case "editar":
                Cliente cli = clienteService.findByDni(cliente.getDni());
                cliente.setIdCliente(cli.getIdCliente());
                if (clienteService.save(cliente)) {
                    resultado = "exito";
                    mensaje = "El cliente se editó con éxito.";
                } else {
                    resultado = "error";
                    mensaje = "Hubo un error al editar al cliente. Inténtelo nuevamente.";
                }
        }
        model.addAttribute(resultado,mensaje);
        return "agregarCliente";
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
}
