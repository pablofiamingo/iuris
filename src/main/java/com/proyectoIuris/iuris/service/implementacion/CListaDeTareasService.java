package com.proyectoIuris.iuris.service.implementacion;

import com.proyectoIuris.iuris.model.ListaDeTareas;
import com.proyectoIuris.iuris.repository.ListaDeTareasRepository;
import com.proyectoIuris.iuris.service.IListaDeTareasService;

import java.util.List;
import java.util.Optional;

public class CListaDeTareasService implements IListaDeTareasService {

    ListaDeTareasRepository listaDeTareasRepository;
    @Override
    public List<ListaDeTareas> getAll() {
        return (List<ListaDeTareas>) listaDeTareasRepository.findAll();
    }

    @Override
    public Optional<ListaDeTareas> findById(int id) {
        return listaDeTareasRepository.findById(id);
    }

    @Override
    public boolean save(ListaDeTareas listToDo) {
        if (listToDo != null) {
            listaDeTareasRepository.save(listToDo);
            return true;
        } else return false;
    } //El save tengo entendido que si no existe lo crea y sino se edita (pero creo que habia fallado, despues revisamos)

    @Override
    public boolean delete(int id) {
        listaDeTareasRepository.deleteById(id);
        return true;
    }
}
