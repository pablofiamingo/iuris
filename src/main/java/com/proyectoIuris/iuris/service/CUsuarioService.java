package com.proyectoIuris.iuris.service;

import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CUsuarioService implements IUsuarioService{

    @Autowired
    private UsuariosRepository uRepo;

    @Override
    public List<Usuario> list() {
        return null;
    }

    @Override
    public void insert(Usuario u) {
        Usuario user = uRepo.save(u);
    }
}
