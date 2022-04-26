package com.proyectoIuris.iuris.service;

import com.proyectoIuris.iuris.model.Usuario;

import java.util.List;

public interface IUsuarioService {
    public List<Usuario> list();
    public void insert(Usuario u);
}
