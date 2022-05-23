package com.proyectoIuris.iuris.service.implementacion;

import com.proyectoIuris.iuris.model.Caso;
import com.proyectoIuris.iuris.repository.CasoRepository;
import com.proyectoIuris.iuris.service.Interfaces.ICasoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CCasoService implements ICasoService {
    @Autowired
    private CasoRepository casoRepo;

    @Override
    public List<Caso> list(int id) {
        return casoRepo.findCasoByIdUsuario(id);
    }

    @Override
    public Caso findCasoById(int idCaso) {
        return casoRepo.findByIdCaso(idCaso);
    }

    @Override
    public boolean delete(int idCaso) {
        casoRepo.deleteByIdCaso(idCaso);
        return false;
    }

    @Override
    public boolean save(Caso caso) {
        if(caso!=null) {
            casoRepo.save(caso);
            return true;
        } else return false;
    }

    @Override
    public boolean update(Caso caso) {
        if(caso!=null) {
            casoRepo.save(caso);
            return true;
        } else return false;
    }

    @Override
    public List<Caso> buscador(String keyword) {
        return casoRepo.buscador(keyword);
    }
}