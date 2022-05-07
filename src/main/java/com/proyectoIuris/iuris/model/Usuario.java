package com.proyectoIuris.iuris.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(nullable = false, length = 50)
    private String user;

    @Column(nullable = false, length = 50)
    private String pass;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 30)
    private String fullName;

    @Column(nullable = false, length = 30)
    private String rol;

    @OneToMany(mappedBy = "usuario")
    private List<Cliente> cliente;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Calendario calendario;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private ListaDeTareas listaDeTareas;
}
