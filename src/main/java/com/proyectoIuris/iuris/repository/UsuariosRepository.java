package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends CrudRepository<Usuario, Integer> {
}
