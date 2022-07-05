package com.proyectoIuris.iuris.service.Interfaces;

import com.proyectoIuris.iuris.model.Cliente;

import java.util.List;

public interface IClienteService {
    /*
    * Acceso abogados
    * */
    public List<Cliente> listPermisoAbogado(int id);
    public List<Cliente> buscadorPermisoAbogado(String keyword, int id);
    /*
    * Acceso general
    * */
    public List<Cliente> list();
    public List<Cliente> buscadorGeneral(String keyword);
    public Cliente findById(int id);
    public Cliente findByDni(String dni);
    public boolean save(Cliente cliente);
    public boolean update(Cliente cliente);
}
