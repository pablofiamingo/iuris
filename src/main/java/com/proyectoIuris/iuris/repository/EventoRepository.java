package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.Evento;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventoRepository extends CrudRepository<Evento, Integer> {

    public @NotNull Evento save(Evento e);

    @Query("SELECT eventos FROM Evento eventos WHERE calendario.idCalendario = :idCal")
    public List<Evento> getEventos(@Param("idCal")int idCal);
    //traer eventos segun id
}
