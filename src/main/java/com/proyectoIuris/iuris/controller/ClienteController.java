package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.Cliente;
//import com.proyectoIuris.iuris.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/buscar")
    public String getClientes() {
        return "testClientes";
    }

    //no esta la interfaz de clienteService en el ultimo merge y rompe el programa
    @PostMapping("/buscador")
    public String buscarCliente(@RequestParam(value = "nombre",required = true) String keyword, Model model) {
        //String keyword = (String) model.getAttribute("nombre");
        List<Cliente> clientes = (List<Cliente>) clienteService.findByNombreOApellido(keyword);
        model.addAttribute("listaClientes", clientes);
        return "testClientes";
    }
}
