package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.Cliente;
import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.service.IClienteService;
import com.proyectoIuris.iuris.service.IArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String getAltaCliente(Model model, HttpSession httpSession) {
        Usuario usuario = (Usuario) httpSession.getAttribute("user");
        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        model.addAttribute("cliente", cliente);
        return "altaCliente";
    }

    @GetMapping("/buscar")
    public String getClientes() {
        return "testClientes";
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

    @GetMapping("/lista")
    public String getListaClientes(HttpSession httpSession, Model model) {
        Usuario usuario = (Usuario) httpSession.getAttribute("user");
        List<Cliente> clientes = clienteService.list(usuario.getIdUsuario());
        model.addAttribute("clientes", clientes);
        return "listaClientes";
    }

    //POST MAPPING-----------------------------------------------------------------------------------------------
    @PostMapping("/buscador")
    public String buscarCliente(@RequestParam(value = "nombre",required = true) String keyword, Model model) {
        List<Cliente> clientes = (List<Cliente>) clienteService.findByNombreOApellido(keyword);
        model.addAttribute("listaClientes", clientes);
        return "testClientes";
    }

    @PostMapping("/alta")
    public String agregarCliente(@RequestPart Cliente cliente, HttpSession session) {
        if(clienteService.save(cliente)) {
            fileService.crearDir(System.getenv("APPDATA") + "\\IURIS\\Archivos\\Clientes\\" + cliente.getIdCliente());
            return "altaCliente";
        } else return "altaCliente";
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


    //TENOG QUE HACER QUE CUANDO CREE EL CLIENTE CREE UN DIRECTORIO

}
