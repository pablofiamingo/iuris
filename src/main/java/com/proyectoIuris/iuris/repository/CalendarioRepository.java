package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.Calendario;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarioRepository extends CrudRepository<Calendario, Integer> {

    public @NotNull Calendario save(Calendario cal);
}
