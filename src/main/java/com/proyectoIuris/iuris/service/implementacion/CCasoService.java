package com.proyectoIuris.iuris.service.implementacion;

import com.proyectoIuris.iuris.model.Caso;
import com.proyectoIuris.iuris.repository.CasoRepository;
import com.proyectoIuris.iuris.service.ICasoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CCasoService implements ICasoService {
    @Autowired
    private CasoRepository casoRepo;

    @Override
    public List<Caso> list(int id) {
        List<Caso> casos = casoRepo.findCasoByIdUsuario(id);
        return casos;
    }

    @Override
    public Caso findCasoById(int idCaso) {
        Caso caso = casoRepo.findByIdCaso(idCaso);
        return caso;
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
}