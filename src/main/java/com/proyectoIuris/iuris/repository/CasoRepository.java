package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.Caso;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasoRepository extends CrudRepository<Caso, Integer> {
    @Query("SELECT c FROM Caso c WHERE c.cliente.usuario.idUsuario = :idUsuario")
    public List<Caso> findCasoByIdUsuario(@Param("idUsuario") int idUsuario);
    void deleteByIdCaso(int idCaso);
    Caso findByIdCaso(int idCaso);
}