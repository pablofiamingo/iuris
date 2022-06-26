package com.proyectoIuris.iuris.service.Interfaces;

import com.proyectoIuris.iuris.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    public List<Usuario> list();
    public Usuario findByUsername(String username);
    public Usuario findByIdUsuario(int id);
    public Usuario insert(Usuario u);
}
