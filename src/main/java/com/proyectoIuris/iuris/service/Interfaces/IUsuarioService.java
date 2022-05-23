package com.proyectoIuris.iuris.service.Interfaces;

import com.proyectoIuris.iuris.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    public List<Usuario> list();
    public Usuario findByUsername(String username);
    public Optional<Usuario> findById(int id);
    public void insert(Usuario u);
}
