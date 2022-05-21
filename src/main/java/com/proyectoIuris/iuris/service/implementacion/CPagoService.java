package com.proyectoIuris.iuris.service.implementacion;

import com.proyectoIuris.iuris.model.Pago;
import com.proyectoIuris.iuris.repository.PagoRepository;
import com.proyectoIuris.iuris.service.Interfaces.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CPagoService implements IPagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public List<Pago> getAll(){
        return (List<Pago>) pagoRepository.findAll();
    }

    @Override
    public Pago insert(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public Optional<Pago> findById(int id) {
        return pagoRepository.findById(id);
    }

    @Override
    public boolean delete(int idPago) {
        pagoRepository.deleteById(idPago);
        return true;
    }
    @Override
    public boolean save(Pago pago) {
        if (pago != null) {
            pagoRepository.save(pago);
            return true;
        } else return false;
    }
}
