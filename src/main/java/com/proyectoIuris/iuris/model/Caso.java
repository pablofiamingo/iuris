package com.proyectoIuris.iuris.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Caso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCaso;
    private String caratula;
    private String link;
    private String materia;
    private String fuero;
    private String estado;
    private String juzgado;
    private String datoOtraParte;
    private String descripcion;
    private String representante;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

}
