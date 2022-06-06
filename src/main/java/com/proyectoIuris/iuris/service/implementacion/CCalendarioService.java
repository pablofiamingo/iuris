package com.proyectoIuris.iuris.service.implementacion;

import com.proyectoIuris.iuris.model.Calendario;
import com.proyectoIuris.iuris.model.Evento;
import com.proyectoIuris.iuris.service.Interfaces.ICalendarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CCalendarioService implements ICalendarioService {


    @Override
    public Calendario save(Calendario cal) {
        return null;
    }

    @Override
    public List<Evento> getEventos() {
        return null;
    }

    @Override
    public void guardarEvento(Evento e) {

    }

    @Override
    public void deleteEvento(int id) {

    }
}
