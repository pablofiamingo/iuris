package com.proyectoIuris.iuris.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

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

    @Column(nullable = false, length = 50)
    private String tarea;

    @Column( columnDefinition = "TINYINT(1)")
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "id_to_do")
    private ListaDeTareas listaDeTareas;

}
