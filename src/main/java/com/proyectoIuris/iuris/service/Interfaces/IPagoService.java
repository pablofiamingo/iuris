package com.proyectoIuris.iuris.service.Interfaces;

import com.proyectoIuris.iuris.model.Pago;

import java.util.List;
import java.util.Optional;

public interface IPagoService {

    public List<Pago> getAll();
    public Pago insert(Pago pago);
    public Optional<Pago> findById(int id);
    public boolean save(Pago pago);
    public boolean delete(int id);
}
