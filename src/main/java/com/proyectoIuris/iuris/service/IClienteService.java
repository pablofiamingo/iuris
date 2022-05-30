package com.proyectoIuris.iuris.service;

import com.proyectoIuris.iuris.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    public List<Cliente> list(int id);
    public Cliente findById(int id);
    public List<Cliente> findByNombreOApellido(String keyword);
    public boolean save(Cliente cliente);
    public boolean update(Cliente cliente);
    public boolean delete(int id);
}
