package com.proyectoIuris.iuris.service.implementacion;

import com.proyectoIuris.iuris.model.Cliente;
import com.proyectoIuris.iuris.repository.ClienteRepository;
import com.proyectoIuris.iuris.service.Interfaces.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CClienteService implements IClienteService {
    @Autowired
    private ClienteRepository clienteRepo;

    @Override
    public List<Cliente> listPermisoAbogado(int id) {
        List<Cliente> listaDeClientes = clienteRepo.findByIdUsuario(id);
        return listaDeClientes;
    }

    @Override
    public Cliente findById(int id) {
        Cliente cliente = clienteRepo.findByIdCliente(id);
        return cliente;
    }

    @Override
    public List<Cliente> buscadorPermisoAbogado(String keyword, int id) {
        List<Cliente> clientes = clienteRepo.findByNombreOApellido(keyword, id);
        return clientes;
    }

    @Override
    public List<Cliente> list() {
        return clienteRepo.list();
    }

    @Override
    public List<Cliente> buscadorGeneral(String keyword) {
        return clienteRepo.findByNombreOApellidoGeneral(keyword);
    }

    @Override
    public Cliente findByDni(String dni) {
        return clienteRepo.findByDni(dni);
    }

    @Override
    public boolean save(Cliente cliente) {
        if (cliente != null) {
            clienteRepo.save(cliente);
            return true;
        } else return false;
    }

    @Override
    public boolean update(Cliente cliente) {
        if (cliente != null) {
            clienteRepo.save(cliente);
            return true;
        } else return false;
    }

}

