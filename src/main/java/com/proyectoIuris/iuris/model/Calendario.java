package com.proyectoIuris.iuris.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Calendario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int idCalendario;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(nullable = false, length = 100)
    private String actividad;

    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    private Date hora;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
