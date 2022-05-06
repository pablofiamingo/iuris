package com.proyectoIuris.iuris.service;

import com.proyectoIuris.iuris.model.Caso;

import java.util.List;
import java.util.Optional;

public interface ICasoService {
    public List<Caso> getAll();
    public Optional<Caso> getPago(int idCaso);
    public Caso insert(Caso caso);
    public void delete(int idCaso);
    public List<Caso> getByCliente(int idCliente);
}
