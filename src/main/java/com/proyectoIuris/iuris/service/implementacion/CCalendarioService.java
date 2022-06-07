package com.proyectoIuris.iuris.service.implementacion;

import com.proyectoIuris.iuris.model.Calendario;
import com.proyectoIuris.iuris.model.Evento;
import com.proyectoIuris.iuris.repository.CalendarioRepository;
import com.proyectoIuris.iuris.repository.EventoRepository;
import com.proyectoIuris.iuris.service.Interfaces.ICalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CCalendarioService implements ICalendarioService {

    @Autowired
    private CalendarioRepository calRepo;
    @Autowired
    private EventoRepository eventRepo;

    @Override
    public Calendario save(Calendario cal) {
        return calRepo.save(cal);
    }
    @Override
    public List<Evento> getEventos(int id) {
        return eventRepo.getEventos(id);
    }
    @Override
    public Evento guardarEvento(Evento e) {
        return eventRepo.save(e);
    }
    @Override
    public void deleteEvento(int id) {
        eventRepo.deleteById(id);
    }
}
