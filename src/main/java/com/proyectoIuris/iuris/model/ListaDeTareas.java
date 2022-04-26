package com.proyectoIuris.iuris.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="lista de tareas")
public class ListaDeTareas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idToDo;
    private String tarea;
    private String relevancia;
    private boolean check;

}
