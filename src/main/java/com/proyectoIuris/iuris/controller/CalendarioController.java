package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.Calendario;
import com.proyectoIuris.iuris.model.Evento;
import com.proyectoIuris.iuris.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/calendario")
public class CalendarioController {

    @PostMapping("/agregar")
    public void agregarEvento(@RequestParam("evento") String evento,
                              @RequestParam("fecha") Date fecha,
                              @RequestParam("color") String color,
                              HttpSession session){

        Usuario user = (Usuario) session.getAttribute("user");
        Calendario calendario = user.getCalendario();
        Evento event = new Evento();
        event.setEvento(evento);
        event.setFecha(fecha);
        event.setColor(color);
        event.setCalendario(calendario);

        //calendarioService.save(event);

    }

}
