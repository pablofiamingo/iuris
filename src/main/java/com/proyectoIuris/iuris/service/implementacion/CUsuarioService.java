package com.proyectoIuris.iuris.service.implementacion;

import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.repository.UsuariosRepository;
import com.proyectoIuris.iuris.service.Interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CUsuarioService implements IUsuarioService {

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
    public Usuario findByIdUsuario(int id) {
        return uRepo.findByIdUsuario(id);
    }

    @Override
    public Usuario insert(Usuario u) {
        return uRepo.save(u);
    }
}
