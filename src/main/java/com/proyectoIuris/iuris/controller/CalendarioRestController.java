package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.Evento;
import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.service.Interfaces.ICalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/restcalendario")
@RestController
public class CalendarioRestController {
    @Autowired
    private ICalendarioService calService;
    @GetMapping("/listar")
    public List<Evento> getCalendario(HttpSession session) {
        Usuario user = (Usuario) session.getAttribute("user");
        return calService.getEventos(user.getCalendario().getIdCalendario());
    }
}
