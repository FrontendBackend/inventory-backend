package com.inventory.backend.dtos;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TblHistorialPrestamoDTO {
    
    /*
     * Identificador primario de la tabla
     */
    private Long idHistorialPrestamo;

    /*
     * Identificador foranea de la tabla prestamo
     */
    private Long idPrestamo;

    /*
     * Fecha de prestamo como historial que se va a generar, debe ser dd/MM/yyyy HH:mm 
     */
    private Date fePrestamoHistorial;

    /*
     * Descripción del historial del prestamo
     */
    private String descripcionHistorial;

    /*
     * El monto subtotal que se obtiene, será restado(-) o sumado(+) y sera registrado como copia en esta tabla
     */
    private BigDecimal montoSubtotalOperado;

    /*
     * Monto que se va a operar cuando lo cancela totalmente o de adelantado
     */
    private BigDecimal montoOperar;

    /* 
     * Me permite ver si es sumado(+) o restado(-) el subtotal
     */
    private String flagOperador;
    
    /*
     * Me permite mostrar el estado del registro
     */
    private String esRegistro;
}
