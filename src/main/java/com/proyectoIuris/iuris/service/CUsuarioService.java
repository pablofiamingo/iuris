package com.proyectoIuris.iuris.service;

import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CUsuarioService implements IUsuarioService{

    @Autowired
    private UsuariosRepository uRepo;

    @Override
    public List<Usuario> list() {
        List<Usuario> usuarios = (List<Usuario>) uRepo.findAll();
        return usuarios;
    }

    @Override
    public Usuario findByUsername(String username) {
        Usuario user = null;
        for (Usuario u : this.list()) {
            if(u.getUser().equals(username)) {
                user = u;
            }
        }
        return user;
    }

    @Override
    public Optional<Usuario> findById(int id) {

        Optional<Usuario> usuario = uRepo.findById(id);

        return usuario;
    }

    @Override
    public void insert(Usuario u) {
        Usuario user = uRepo.save(u);
    }
}
