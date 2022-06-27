package com.proyectoIuris.iuris.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPago;

    @Column(nullable = false, length = 30)
    private double cantAbonada;

    @Column(nullable = false, length = 50)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(nullable = false, length = 50)
    private String formaPago;

    /*public void setFecha(String date){
        try {
            this.fecha = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setFecha(Date date) {
        this.fecha = date;
    }
*/
    @ManyToOne
    @JoinColumn(name = "id_caso")
    private Caso caso;
}
