package com.inventory.backend.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "TBL_HISTORIAL_PRESTAMO")
public class TblHistorialPrestamo implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * Identificador primario de la tabla
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HISTORIAL_PRESTAMO", nullable = false)
    private Long idHistorialPrestamo;

    /*
     * Identificador foranea de la tabla prestamo
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRESTAMO", nullable = false)
    private TblPrestamo tblPrestamo;

    /*
     * Fecha de prestamo como historial que se va a generar, debe ser dd/MM/yyyy HH:mm 
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FE_PRESTAMO_HISTORIAL", nullable = false)
    private Date fePrestamoHistorial;

    /*
     * Descripción del historial del prestamo
     */
    @Column(name = "DESCRIPCION_HISTORIAL", nullable = false)
    private String descripcionHistorial;

    /*
     * El monto subtotal que se obtiene, será restado(-) o sumado(+) y sera registrado como copia en esta tabla
     */
    @Column(name = "MONTO_SUBTOTAL_OPERADO", nullable = false)
    private BigDecimal montoSubtotalOperado;

    /*
     * Monto que se va a operar cuando lo cancela totalmente o de adelantado
     */
    @Column(name = "MONTO_OPERAR", nullable = false)
    private BigDecimal montoOperar;

    /* 
     * Me permite ver si es sumado(+) o restado(-) el subtotal
     */
    @Column(name = "FLAG_OPERADOR", nullable = false)
    private String flagOperador;
    
    /*
     * Me permite mostrar el estado del registro
     */
    @Column(name = "ES_REGISTRO", nullable = false)
    private String esRegistro;
}
