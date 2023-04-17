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
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "TBL_PRESTAMO")
@NoArgsConstructor
public class TblPrestamo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRESTAMO", nullable = false)
    private Long idPrestamo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERSONA", nullable = false)
    private TblPersona tblPersona;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FE_PRESTAMO", nullable = false)
    private Date fePrestamo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FE_CANCELADO", nullable = false)
    private Date feCancelado;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "MONTO", nullable = false)
    private BigDecimal monto;

    @Column(name = "ESTADO", nullable = false)
    private String estado;
    
    @Column(name = "ES_REGISTRO", nullable = false)
    private String esRegistro;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FE_CREACION", nullable = false)
    private Date feCreacion;
}
