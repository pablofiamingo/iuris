package com.proyectoIuris.iuris.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne
    @JoinColumn(name = "id_calendario", updatable = false, nullable = false)
    private Calendario calendario;

    @OneToOne
    @JoinColumn(name = "id_to_do", updatable = false, nullable = false)
    private ListaDeTareas listaDeTareas;
}
