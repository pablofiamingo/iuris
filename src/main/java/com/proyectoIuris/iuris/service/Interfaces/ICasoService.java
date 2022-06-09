package com.proyectoIuris.iuris.service.Interfaces;

import com.proyectoIuris.iuris.model.Caso;

import java.util.List;

public interface ICasoService {
    public List<Caso> list(int id); //lista los casos segun el id del usuario
    public Caso findCasoById(int idCaso); //
    public boolean delete(int idCaso);
    public boolean save(Caso caso);
    public boolean update(Caso caso);
    public List<Caso> buscador(String keyword, int id);
}


