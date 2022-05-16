package com.proyectoIuris.iuris.service;

import com.proyectoIuris.iuris.model.ListaDeTareas;
import com.proyectoIuris.iuris.model.Pago;

import java.util.List;
import java.util.Optional;

public interface IListaDeTareasService {

    public List<ListaDeTareas> getAll();
    public Optional<ListaDeTareas> findById(int id);
    public boolean save(ListaDeTareas listToDo);
    public boolean delete(int id);
}
