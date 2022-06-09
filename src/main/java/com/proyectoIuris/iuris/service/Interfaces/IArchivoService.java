package com.proyectoIuris.iuris.service.Interfaces;

import com.proyectoIuris.iuris.model.Archivo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IArchivoService {

    public void uploadToLocal(MultipartFile file, String ruta); //guarda el archivo
    public List<Archivo> list(int idUser, int idCaso); //lista los archivos
    public Archivo findById(int id); //trae archivo por id
    public boolean crearDir(String directorio); //crea la ruta local para el pdf
    public boolean insert(Archivo archivo); //crea archivo en bd
    public boolean delete(int id); //elimina el archivo de la bd, pero no de la carpeta... investigar esto
}
