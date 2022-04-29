package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.Pago;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends CrudRepository<Pago, Integer> {
}
