package com.inventory.backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventory.backend.dtos.TblHistorialPrestamoDTO;
import com.inventory.backend.model.TblHistorialPrestamo;

public interface HistorialPrestamoRepository extends JpaRepository<TblHistorialPrestamo, Long> {

    @Query("SELECT new com.inventory.backend.dtos.TblHistorialPrestamoDTOResultado( "
            + "p.idHistorialPrestamo, p.tblPrestamo.idPrestamo, p.fePrestamoHistorial, p.descripcionHistorial, "
            + "montoSubtotalOperado, montoOperar, flagOperador "
            + ") "
            + "FROM TblHistorialPrestamo p "
            + "WHERE p.esRegistro = '1' "
            + "AND p.tblPrestamo.idPrestamo = :idPrestamo "
            )
    Page<TblHistorialPrestamoDTO> paginarHistorialPrestamos(@Param("idPrestamo") Long idPrestamo, Pageable paginador);

    @Query("SELECT new com.inventory.backend.dtos.TblHistorialPrestamoDTOResultado( "
            + "p.idHistorialPrestamo, p.tblPrestamo.idPrestamo, p.fePrestamoHistorial, p.descripcionHistorial, "
            + "montoSubtotalOperado, montoOperar, flagOperador "
            + ") "
            + "FROM TblHistorialPrestamo p "
            + "WHERE p.idHistorialPrestamo = :idHistorialPrestamo ")
    TblHistorialPrestamoDTO obtenerHistorialPrestamoPorId(@Param("idHistorialPrestamo") Long idHistorialPrestamo);
}
