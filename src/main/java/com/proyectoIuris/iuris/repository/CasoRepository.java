package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.Caso;
import com.proyectoIuris.iuris.model.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasoRepository extends CrudRepository<Caso, Integer> {
    @Query("SELECT c FROM Caso c WHERE c.cliente.usuario.idUsuario = :idUsuario")
    List<Caso> findCasoByIdUsuario(@Param("idUsuario") int idUsuario);
    void deleteByIdCaso(int idCaso);
    Caso findByIdCaso(int idCaso);
    @Query("SELECT c FROM Caso c WHERE c.cliente.usuario.idUsuario=:id AND c.materia LIKE %:keyword% OR c.caratula LIKE %:keyword% OR c.fuero LIKE %:keyword%")
    List<Caso> buscador(@Param("keyword")String keyword, @Param("id")int id);

    @Query("SELECT c FROM Caso c WHERE c.cliente.idCliente = :idCliente")
    List<Caso> findCasoByIdCliente(@Param("idCliente") int idCliente);
}