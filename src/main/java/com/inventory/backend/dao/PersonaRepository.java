package com.inventory.backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventory.backend.dtos.TblPersonaDTO;
import com.inventory.backend.model.TblPersona;

public interface PersonaRepository extends JpaRepository<TblPersona, Long>{
    
    @Query("SELECT new com.inventory.backend.dtos.TblPersonaDTOResultado( "
            + "p.idPersona, p.noPersona, p.apPaterno, p.apMaterno  "
            + ") " 
            + "FROM TblPersona p "
            + "WHERE p.esRegistro = '1' "
            )
    Page<TblPersonaDTO> paginarPersonas(Pageable paginador);

    @Query("SELECT new com.inventory.backend.dtos.TblPersonaDTOResultado( "
            + "p.idPersona, p.noPersona, p.apPaterno, p.apMaterno  "
            + ") " 
            + "FROM TblPersona p "
            + "WHERE p.idPersona = :idPersona "
            )
    TblPersonaDTO obtenerPersonaPorId( @Param("idPersona") Long idPersona);
    
}
