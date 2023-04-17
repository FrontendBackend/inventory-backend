package com.inventory.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TblPersonaDTO {
    
    private Long idPersona;

	private String noPersona;

	private String apPaterno;

	private String apMaterno;

	private String esRegistro;
}
