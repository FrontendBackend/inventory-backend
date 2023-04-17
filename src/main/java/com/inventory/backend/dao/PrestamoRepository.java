package com.inventory.backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventory.backend.dtos.TblPrestamoDTO;
import com.inventory.backend.model.TblPrestamo;

public interface PrestamoRepository extends JpaRepository<TblPrestamo, Long> {

    @Query("SELECT new com.inventory.backend.dtos.TblPrestamoDTOResultado( "
            + "p.idPrestamo, p.tblPersona.idPersona, p.fePrestamo, p.feCancelado, p.descripcion, p.monto, p.estado  "
            + ") "
            + "FROM TblPrestamo p "
            + "WHERE p.esRegistro = '1' " 
            + "AND p.tblPersona.idPersona = :idPersona ")
    Page<TblPrestamoDTO> paginarPrestamos(@Param("idPersona") Long idPersona, Pageable paginador);

    @Query("SELECT new com.inventory.backend.dtos.TblPrestamoDTOResultado( "
            + "p.idPrestamo, p.tblPersona.idPersona, p.fePrestamo, p.feCancelado, p.descripcion, p.monto, p.estado "
            + ") "
            + "FROM TblPrestamo p "
            + "WHERE p.idPrestamo = :idPrestamo ")
    TblPrestamoDTO obtenerPrestamoPorId(@Param("idPrestamo") Long idPrestamo);
}
