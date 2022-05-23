package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.ListaDeTareas;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ListaDeTareasRepository extends CrudRepository<ListaDeTareas, Integer> {

    @Query("SELECT lista FROM ListaDeTareas lista WHERE lista.usuario.idUsuario = :idUser ")
    public ListaDeTareas findById(@Param("idUser") int idUsuario);

}
