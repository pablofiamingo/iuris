package com.proyectoIuris.iuris.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Caso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCaso;

    @Column(nullable = false, length = 50)
    private String caratula;

    @Column(nullable = false, length = 50)
    private String materia;

    @Column(nullable = false, length = 50)
    private String fuero;

    @Column(nullable = false, length = 50)
    private String estado;

    @Column(nullable = false, length = 50)
    private String juzgado;

    @Column(nullable = false, length = 80)
    private String datoOtraParte;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false, length = 50)
    private String representante;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "caso")
    private List<Pago> pagoList;

    @OneToMany(mappedBy = "caso")
    private List<Archivo> archivos;


}
