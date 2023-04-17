package com.inventory.backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.inventory.backend.dtos.TblHistorialPrestamoDTO;
import com.inventory.backend.dtos.TblPersonaDTO;
import com.inventory.backend.dtos.TblPrestamoDTO;
import com.inventory.backend.model.TblHistorialPrestamo;
import com.inventory.backend.model.TblPersona;
import com.inventory.backend.model.TblPrestamo;

public interface PersonaService {

    /*
     * -----------------------------------------------
     * ----------- CRUD de persona -------------------
     * -----------------------------------------------
     */
    Page<TblPersonaDTO> paginarPersonas(Pageable paginador);

    TblPersonaDTO obtenerPersonaPorId(Long idPersona);

    TblPersona obtenerPersonaById(Long idPersona);

    TblPersonaDTO crearPersona(TblPersonaDTO tblPersonaDTO) throws Exception;

    TblPersonaDTO modificarPersona(TblPersonaDTO tblPersonaDTO, TblPersona tblPersona) throws Exception;

    Long eliminarPersona(Long idPersona) throws Exception;

    /*
     * -----------------------------------------------
     * ----------- CRUD de prestamo ------------------
     * -----------------------------------------------
     */
    Page<TblPrestamoDTO> paginarPrestamos(Long idPersona, Pageable paginador);

    TblPrestamoDTO obtenerPrestamoPorId(Long idPrestamo);

    TblPrestamo obtenerPrestamoById(Long idPrestamo);

    TblPrestamoDTO crearPrestamo(TblPrestamoDTO tblPrestamoDTO) throws Exception;

    TblPrestamoDTO modificarPrestamo(TblPrestamoDTO tblPrestamoDTO, TblPrestamo tblPrestamo) throws Exception;

    Long eliminarPrestamo(Long idPrestamo) throws Exception;

    /*
     * -----------------------------------------------
     * ----------- CRUD de historial prestamo --------
     * -----------------------------------------------
     */
    Page<TblHistorialPrestamoDTO> paginarHistorialPrestamos(Long idPrestamo, Pageable paginador);

    TblHistorialPrestamoDTO obtenerHistorialPrestamoPorId(Long idHistorialPrestamo);

    TblHistorialPrestamo obtenerHistorialPrestamoById(Long idHistorialPrestamo);

    TblHistorialPrestamoDTO crearHistorialPrestamo(TblHistorialPrestamoDTO tblHistorialPrestamoDTO) throws Exception;

    TblHistorialPrestamoDTO modificarHistorialPrestamo(TblHistorialPrestamoDTO tblHistorialPrestamoDTO,
            TblHistorialPrestamo tblHistorialPrestamo) throws Exception;

    Long eliminarHistorialPrestamo(Long idHistorialPrestamo) throws Exception;

}
