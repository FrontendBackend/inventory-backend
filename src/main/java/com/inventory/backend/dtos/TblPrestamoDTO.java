package com.inventory.backend.dtos;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TblPrestamoDTO {
    
    private Long idPrestamo;

    private Long idPersona;

    private Date fePrestamo;

    private Date feCancelado;

    private String descripcion;

    private BigDecimal monto;

    private String estado;
    
    private String esRegistro;

    private Date feCreacion;

}
