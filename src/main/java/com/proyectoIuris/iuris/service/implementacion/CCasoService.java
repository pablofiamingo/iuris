package com.proyectoIuris.iuris.service.implementacion;

import com.proyectoIuris.iuris.model.Caso;
import com.proyectoIuris.iuris.model.Usuario;
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
    public List<Caso> listPermisoAbogado(int id) {
        return casoRepo.listPermisoAbogado(id);
    }

    @Override
    public List<Caso> buscadorGeneral(String keyword) {
       return casoRepo.buscadorGeneral(keyword);
    }

    @Override
    public List<Caso> list() {
        return casoRepo.list();
    }

    @Override
    public Caso findCasoById(int idCaso) {
        return casoRepo.findByIdCaso(idCaso);
    }

    @Override
    public boolean delete(int idCaso) {
        casoRepo.deleteById(idCaso);
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
    public List<Caso> buscadorPermisoAbogado(String keyword, int id) {
        return casoRepo.buscadorPermisoAbogado(keyword, id);
    }



    @Override
    public List<Caso> findCasoByIdCliente(int idCliente) {
        return casoRepo.findCasoByIdCliente(idCliente);
    }
}