package com.proyectoIuris.iuris.service;

import com.proyectoIuris.iuris.model.Pago;
import com.proyectoIuris.iuris.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IPagoService {

    public List<Pago> getAll();
    public Optional<Pago> getPago(int idPago);
    public Pago insert(Pago pago);
    public void delete(int idPago);
}
