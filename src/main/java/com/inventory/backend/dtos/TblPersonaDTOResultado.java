package com.inventory.backend.dtos;

public class TblPersonaDTOResultado extends TblPersonaDTO {
    
    public TblPersonaDTOResultado(Long idPersona, String noPersona, String apPaterno, String apMaterno) {
        super();
        this.setIdPersona(idPersona);
        this.setNoPersona(noPersona);
        this.setApPaterno(apPaterno);
        this.setApMaterno(apMaterno);
    }
}
