package com.proyectoIuris.iuris.service.Interfaces;

import com.proyectoIuris.iuris.model.Caso;

import java.util.List;

public interface ICasoService {
    /*
    * Acceso abogados
    * */
    public List<Caso> buscadorPermisoAbogado(String keyword, int id);
    public List<Caso> listPermisoAbogado(int id);
    /*
    * Acceso general
    * */
    public List<Caso> buscadorGeneral(String keyword);
    public List<Caso> list();
    public Caso findCasoById(int idCaso);
    public boolean delete(int idCaso);
    public boolean save(Caso caso);
    public boolean update(Caso caso);
    public List<Caso> findCasoByIdCliente(int idCliente);
}


