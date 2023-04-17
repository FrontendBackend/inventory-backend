package com.inventory.backend.dtos;

import java.math.BigDecimal;
import java.util.Date;

public class TblHistorialPrestamoDTOResultado extends TblHistorialPrestamoDTO {

    public TblHistorialPrestamoDTOResultado(
            Long idHistorialPrestamo,
            Long idPrestamo,
            Date fePrestamoHistorial,
            String descripcionHistorial,
            BigDecimal montoSubtotalOperado,
            BigDecimal montoOperar,
            String flagOperador) {
        super();
        this.setIdHistorialPrestamo(idHistorialPrestamo);
        this.setIdPrestamo(idPrestamo);
        this.setFePrestamoHistorial(fePrestamoHistorial);
        this.setDescripcionHistorial(descripcionHistorial);
        this.setMontoSubtotalOperado(montoSubtotalOperado);
        this.setMontoOperar(montoOperar);
        this.setFlagOperador(flagOperador);
    }
}
