package com.proyectoIuris.iuris.service.Interfaces;

import com.proyectoIuris.iuris.model.DetalleTarea;
import com.proyectoIuris.iuris.model.ListaDeTareas;

import java.util.List;

public interface IListaDeTareasService {

    //public List<ListaDeTareas> getAll();
    public ListaDeTareas findById(int idUsuario); //traer una lista por id del user
    public boolean save(ListaDeTareas listToDo);
    public boolean delete(int id);

    //detalles
    public DetalleTarea getTarea(int id);
    public List<DetalleTarea> getTareas(int id);
    public void guardarTarea(DetalleTarea tarea);
    public void deleteTarea(int id);

}
