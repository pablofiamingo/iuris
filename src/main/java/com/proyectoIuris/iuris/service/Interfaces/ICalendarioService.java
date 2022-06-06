package com.proyectoIuris.iuris.service.Interfaces;

import com.proyectoIuris.iuris.model.Calendario;
import com.proyectoIuris.iuris.model.Evento;

import java.util.List;

public interface ICalendarioService {
    public Calendario save(Calendario cal);

    //eventos
    public List<Evento> getEventos();//poner id
    public void guardarEvento(Evento e);
    public void deleteEvento(int id);
}
