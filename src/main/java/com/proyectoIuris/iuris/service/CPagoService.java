package com.proyectoIuris.iuris.service;

import com.proyectoIuris.iuris.model.Pago;
import com.proyectoIuris.iuris.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CPagoService implements IPagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public List<Pago> getAll(){
        return (List<Pago>) pagoRepository.findAll();
    }

    @Override
    public Optional<Pago> getPago(int idPago){
        return pagoRepository.findById(idPago);
    }

    @Override
    public Pago insert(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public void delete(int idPago){
        pagoRepository.deleteById(idPago);
  }
}
