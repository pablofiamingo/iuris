package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.repository.UsuariosRepository;
import com.proyectoIuris.iuris.service.CUsuarioService;
import com.proyectoIuris.iuris.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class TestUsuarioController {

    @Autowired
    private IUsuarioService uService;

}
