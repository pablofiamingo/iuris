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
@Table(name="lista_de_tareas")
public class ListaDeTareas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idToDo;

    @Column(nullable = false, length = 50)
    private String tarea;

    @Column(nullable = false, length = 250)
    private String relevancia;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}
