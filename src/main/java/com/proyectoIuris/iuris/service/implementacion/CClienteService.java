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
    public List<Cliente> list(int id) {
        List<Cliente> listaDeClientes = (List<Cliente>) clienteRepo.findByIdUsuario(id);
        return listaDeClientes;
    }

    @Override
    public Cliente findById(int id) {
        Cliente cliente = clienteRepo.findByIdCliente(id);
        return cliente;
    }

    @Override
    public List<Cliente> findByNombreOApellido(String keyword) {
        List<Cliente> clientes = (List<Cliente>) clienteRepo.findByNombreOApellido(keyword);
        return clientes;
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

    @Override
    public boolean delete(int idCliente) {
        clienteRepo.deleteByIdCliente(idCliente);
        return true;
    }
}
