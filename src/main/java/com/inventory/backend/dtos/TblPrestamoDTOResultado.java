package com.inventory.backend.dtos;

import java.math.BigDecimal;
import java.util.Date;

public class TblPrestamoDTOResultado extends TblPrestamoDTO {

    public TblPrestamoDTOResultado(Long idPrestamo, Long idPersona, Date fePrestamo, Date feCancelado,
            String descripcion, BigDecimal monto, String estado) {
        super();
        this.setIdPrestamo(idPrestamo);
        this.setIdPersona(idPersona);
        this.setFePrestamo(fePrestamo);
        this.setFeCancelado(feCancelado);
        this.setDescripcion(descripcion);
        this.setMonto(monto);
        this.setEstado(estado);
    }
}
