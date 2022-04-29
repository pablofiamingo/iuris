package com.proyectoIuris.iuris.service;

import com.proyectoIuris.iuris.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    public List<Usuario> list();

    //hacer un findByUsername que reciba la lista y devuelva un unico Usuario
    public Optional<Usuario> findById(int id);
    public void insert(Usuario u);
}
