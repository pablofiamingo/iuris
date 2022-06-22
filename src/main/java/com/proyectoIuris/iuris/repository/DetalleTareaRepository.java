package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.DetalleTarea;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetalleTareaRepository extends CrudRepository<DetalleTarea, Integer> {

    @Query("SELECT detalles FROM DetalleTarea detalles WHERE listaDeTareas.idToDo = :idLista")
    public List<DetalleTarea> getTareas(@Param("idLista")int idLista);

    @Query("SELECT detalles FROM DetalleTarea detalles WHERE idDetalleTarea = :idTarea")
    public DetalleTarea findById(@Param("idTarea")int id);
    public DetalleTarea save(DetalleTarea tarea);

}
