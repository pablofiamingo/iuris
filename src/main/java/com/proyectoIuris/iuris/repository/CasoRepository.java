package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.Caso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasoRepository extends CrudRepository<Caso, Integer> {
    List<Caso> findByIdClienteOrderByApellidoAsc(int idCliente);
}
