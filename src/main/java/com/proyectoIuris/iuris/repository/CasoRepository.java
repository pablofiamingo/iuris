package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.Caso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasoRepository extends CrudRepository<Caso, Integer> {
}
