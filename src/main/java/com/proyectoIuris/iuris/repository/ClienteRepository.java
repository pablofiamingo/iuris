package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
    /*
    * Acceso abogados
    * */
    @Query("SELECT cli FROM Cliente cli WHERE cli.usuario.idUsuario=:id AND cli.nombre LIKE %:keyword% OR cli.apellido LIKE %:keyword%")
    public List<Cliente> findByNombreOApellido(@Param("keyword")String keyword, @Param("id")int id);
    @Query("SELECT cli FROM Cliente cli WHERE cli.usuario.idUsuario = :idUser")
    public List<Cliente> findByIdUsuario(@Param("idUser") int idUser);
    /*
    * Acceso general
    * */
    @Query("SELECT cli FROM Cliente cli WHERE cli.nombre LIKE %:keyword% OR cli.apellido LIKE %:keyword%")
    public List<Cliente> findByNombreOApellidoGeneral(@Param("keyword")String keyword);
    @Query("SELECT cli FROM Cliente cli")
    public List<Cliente> list();
    @Query("SELECT cli FROM Cliente cli WHERE cli.idCliente = :idCliente")
    public Cliente findByIdCliente(@Param("idCliente") int idCliente);
    @Query("SELECT cli FROM Cliente cli WHERE cli.dni=:dni")
    public Cliente findByDni(@Param("dni") String dni);

}
