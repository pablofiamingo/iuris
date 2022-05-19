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
@Table(name="detalle_tareas")
public class DetalleTarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetalleTarea;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_to_do")
    private ListaDeTareas listaDeTareas;

}
