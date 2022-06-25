package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.Caso;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasoRepository extends CrudRepository<Caso, Integer> {
    /*
    * Acceso abogados
    * */
    @Query("SELECT c FROM Caso c WHERE c.cliente.usuario.idUsuario = :idUsuario")
    List<Caso> listPermisoAbogado(@Param("idUsuario") int idUsuario);
    @Query("SELECT c FROM Caso c WHERE c.cliente.usuario.idUsuario=:id AND c.materia LIKE %:keyword% OR c.caratula LIKE %:keyword% OR c.fuero LIKE %:keyword%")
    List<Caso> buscadorPermisoAbogado(@Param("keyword")String keyword, @Param("id")int id);
    /*
    * Acceso general
    * */
    @Query("SELECT c FROM Caso c WHERE c.materia LIKE %:keyword% OR c.caratula LIKE %:keyword% OR c.fuero LIKE %:keyword%")
    List<Caso> buscadorGeneral(@Param("keyword")String keyword);
    @Query("SELECT c from Caso c")
    public List<Caso> list();
    @Override
    void deleteById(Integer integer);
    Caso findByIdCaso(int idCaso);
    @Query("SELECT c FROM Caso c WHERE c.cliente.idCliente = :idCliente")
    List<Caso> findCasoByIdCliente(@Param("idCliente") int idCliente);
}