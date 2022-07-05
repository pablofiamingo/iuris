package com.proyectoIuris.iuris.service.implementacion;

import com.proyectoIuris.iuris.model.DetalleTarea;
import com.proyectoIuris.iuris.model.ListaDeTareas;
import com.proyectoIuris.iuris.repository.DetalleTareaRepository;
import com.proyectoIuris.iuris.repository.ListaDeTareasRepository;
import com.proyectoIuris.iuris.service.Interfaces.IListaDeTareasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CListaDeTareasService implements IListaDeTareasService {

    @Autowired
    private ListaDeTareasRepository listaDeTareasRepository;

    @Autowired
    private DetalleTareaRepository detalleRepo;

    @Override
    public ListaDeTareas findById(int idUsuario) {
        return listaDeTareasRepository.findById(idUsuario);
        /*
        * hacer que cuando se registre el usuario se le cree una lista y un calendario
        * */
    }

    @Override
    public boolean save(ListaDeTareas listToDo) {
        if (listToDo != null) {
            listaDeTareasRepository.save(listToDo);
            return true;
        } else return false;
    }

    @Override
    public boolean delete(int id) {
        listaDeTareasRepository.deleteById(id);
        return true;
    }

    @Override
    public DetalleTarea getTarea(int id) {
        return detalleRepo.findById(id);
    }

    @Override
    public List<DetalleTarea> getTareas(int id) {
        return detalleRepo.getTareas(id);
    }

    @Override
    public void guardarTarea(DetalleTarea tarea) {
        detalleRepo.save(tarea);
    }

    @Override
    public void deleteTarea(int id) {
        detalleRepo.deleteById(id);
    }
}
