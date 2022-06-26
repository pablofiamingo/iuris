package com.proyectoIuris.iuris.service.implementacion;

import com.proyectoIuris.iuris.model.Pago;
import com.proyectoIuris.iuris.repository.PagoRepository;
import com.proyectoIuris.iuris.service.Interfaces.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CPagoService implements IPagoService {

    @Autowired
    private PagoRepository pagoRepo;

    @Override
    public Pago findPagoById(int idPago) {
        return pagoRepo.findByIdPago(idPago);
    }

    @Override
    public boolean delete(int idPago) {
        pagoRepo.deleteByIdPago(idPago);
        return false;
    }

    @Override
    public boolean save(Pago pago) {
        if(pago!=null) {
            pagoRepo.save(pago);
            return true;
        } else return false;
    }

    @Override
    public boolean update(Pago pago) {
        if(pago!=null) {
            pagoRepo.save(pago);
            return true;
        } else return false;
    }

   @Override
    public List<Pago> findPagoByIdCaso(int idCaso) {
        return pagoRepo.findPagoByIdCaso(idCaso);
    }
}
