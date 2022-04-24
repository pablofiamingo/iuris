package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.Caso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CasoRepository extends JpaRepository<Caso, Integer> {
}
