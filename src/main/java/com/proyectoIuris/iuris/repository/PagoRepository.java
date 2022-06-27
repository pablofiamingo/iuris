package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.Pago;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends CrudRepository<Pago, Integer> {
    public void deleteById(Integer integer);
    Pago findByIdPago(int idPago);
    @Query("SELECT p FROM Pago p WHERE p.caso.idCaso = :idCaso")
    List<Pago> findPagoByIdCaso(@Param("idCaso") int idCaso);
}
