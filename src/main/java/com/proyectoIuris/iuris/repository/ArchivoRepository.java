package com.proyectoIuris.iuris.repository;

import com.proyectoIuris.iuris.model.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {

    @Query("SELECT file FROM Archivo file WHERE file.caso.cliente.usuario.idUsuario = :idUser")
    public List<Archivo> list(@Param("idUser") int idUser);

    public Archivo findById(int id); //para abrirlo

}
