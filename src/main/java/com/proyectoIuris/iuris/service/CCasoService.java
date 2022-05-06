package com.proyectoIuris.iuris.service;

import com.proyectoIuris.iuris.model.Caso;
import com.proyectoIuris.iuris.repository.CasoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CCasoService implements  ICasoService {
    @Autowired
    private CasoRepository casoRepository;

    @Override
    public List<Caso> getAll() {
        return (List<Caso>) casoRepository.findAll();
    }

    @Override
    public Optional<Caso> getPago(int idCaso) {
        return casoRepository.findById(idCaso);
    }

    @Override
    public Caso insert(Caso caso) {
        return casoRepository.save(caso);
    }

    @Override
    public void delete(int idCaso) {
        casoRepository.deleteById(idCaso);
    }

    @Override
    public List<Caso> getByCliente(int idCliente) {
        return casoRepository.findByIdClienteOrderByApellidoAsc(idCliente);
    }

}
