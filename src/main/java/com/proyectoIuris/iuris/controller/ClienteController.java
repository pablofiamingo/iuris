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
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/alta")
    public String getAltaCliente(Model model, HttpSession session) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Util.mostrarAlertas(model,session,"agregarCliente");

        Usuario usuario = (Usuario) session.getAttribute("user");
        Cliente cliente = new Cliente();
        boolean abogado = false;

        if (usuario.getRol().equals("abogado")) {
            cliente.setUsuario(usuario);
            abogado = true;
        } else {
            List<Usuario> usuarios = usuarioService.list();
            usuarios.removeIf(u -> !u.getRol().equals("abogado"));
            model.addAttribute("usuarios", usuarios);
        }

        model.addAttribute("abogado", abogado);
        model.addAttribute("cliente", cliente);
        return "agregarCliente";
    }

    @GetMapping("/editar/{id}")
    public String getEditarCliente(Model model, HttpSession session, @PathVariable("id") int id) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Util.mostrarAlertas(model, session, "editarCliente");

        Usuario usuarioActivo = (Usuario) session.getAttribute("user");
        Cliente cliente = clienteService.findById(id);
        boolean abogado = false;

        if (!usuarioActivo.getRol().equals("abogado")) {
            List<Usuario> usuarios = usuarioService.list();
            usuarios.removeIf(u -> !u.getRol().equals("abogado"));
            model.addAttribute("usuarios", usuarios);
        } else {
            abogado = true;
        }

        model.addAttribute("abogado", abogado);
        model.addAttribute("cliente", cliente);
        return "editarCliente";
    }

    @GetMapping("/lista")
    public String getListaClientes(HttpSession session, Model model) {

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
    public String agregarCliente(@Validated Cliente cliente, HttpSession session, @RequestParam(value = "repr",required = false,defaultValue = "0")int repr) {
        String estado = "";

        if (clienteService.findByDni(cliente.getDni()) != null){
            estado = "error"; //si el dni ya existe en la bd se devuelve una alerta de error
        } else {
            if (repr != 0) cliente.setUsuario(usuarioService.findByIdUsuario(repr)); //si se proporciona un nuevo representante, se setea en el cliente
            estado = clienteService.save(cliente) ? "exito" : "error"; //se guarda el cliente almacenando el resultado en la variable estado
            if (estado.equals("exito")) fileService.crearDir(System.getenv("APPDATA") + "\\IURIS\\Archivos\\Clientes\\" + cliente.getIdCliente());
            //si dio de resultado true (exito) se crea un directorio para el cliente
        }

        session.setAttribute("agregarCliente", estado);
        return "redirect:/cliente/alta";
    }

    @PostMapping("/editar")
    public String editarCliente(@Validated Cliente cliente, HttpSession session, @RequestParam(value = "repr", required = false,defaultValue = "0")int repr) {
        Usuario user = (Usuario) session.getAttribute("user");

        if (repr != 0) {
            cliente.setUsuario(usuarioService.findByIdUsuario(repr)); //si se indica un nuevo abogado, el mismo se setea al cliente
            //esto es cuando el usuario activo es empleado o admin
        } else {
            cliente.setUsuario(user); //si el usuario es abogado se setea al cliente
        }

        String estado = clienteService.save(cliente) ? "exito" : "error" ;
        session.setAttribute("editarCliente",estado);
        return "redirect:/cliente/editar/" + cliente.getIdCliente();
    }

}
