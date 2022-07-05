package com.proyectoIuris.iuris.service.Interfaces;

import com.proyectoIuris.iuris.model.Pago;
import java.util.List;


public interface IPagoService {

    public Pago findPagoById(int idPago);
    public void delete(int idPago);
    public boolean save(Pago Pago);
    public boolean update(Pago Pago);
    public List<Pago> findPagoByIdCaso(int idCaso);

}
