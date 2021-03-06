package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.Calendario;
import com.proyectoIuris.iuris.model.Evento;
import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.service.Interfaces.ICalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/calendario")
public class CalendarioController {
    @Autowired
    private ICalendarioService calService;
    @PostMapping("/agregar")
    public String agregarEvento(@RequestParam(value = "title") String title, @RequestParam(value = "start") String fecha, @RequestParam(value = "color") String color, HttpSession session) throws ParseException {

        Usuario user = (Usuario) session.getAttribute("user");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaFormateada = sdf.parse(fecha);
        Evento evento = new Evento();
        Calendario calendario = user.getCalendario();

        evento.setTitle(title);
        evento.setColor(color);
        evento.setStart(fechaFormateada);
        evento.setCalendario(calendario);

        calService.guardarEvento(evento);
        return "redirect:/inicio";
    }

    @PostMapping("/eliminar")
    public String eliminarEvento(@RequestParam("id") int id) {
        calService.deleteEvento(id);
        return "redirect:/inicio";
    }
}
