package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends CrudRepository<Usuario, Integer> {
    public Usuario findByIdUsuario(int id);
    public Usuario save(Usuario usuario);
    public Usuario findByEmail(String email); //Metodo para encontrar el mail del usuario
}
