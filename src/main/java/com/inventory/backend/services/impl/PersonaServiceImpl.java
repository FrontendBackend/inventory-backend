package com.inventory.backend.services.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.backend.dao.HistorialPrestamoRepository;
import com.inventory.backend.dao.PersonaRepository;
import com.inventory.backend.dao.PrestamoRepository;
import com.inventory.backend.dtos.TblHistorialPrestamoDTO;
import com.inventory.backend.dtos.TblPersonaDTO;
import com.inventory.backend.dtos.TblPrestamoDTO;
import com.inventory.backend.model.TblHistorialPrestamo;
import com.inventory.backend.model.TblPersona;
import com.inventory.backend.model.TblPrestamo;
import com.inventory.backend.services.PersonaService;
import com.inventory.backend.utils.CommonsUtil;
import com.inventory.backend.utils.ConstantesUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private HistorialPrestamoRepository historialPrestamoRepository;

    /*
     * -----------------------------------------------
     * ----------- CRUD de persona -------------------
     * -----------------------------------------------
     */
    @Override
    public Page<TblPersonaDTO> paginarPersonas(Pageable paginador) {
        Page<TblPersonaDTO> pTblPersonaDTO = this.personaRepository.paginarPersonas(paginador);
        return pTblPersonaDTO;
    }

    @Override
    public TblPersonaDTO obtenerPersonaPorId(Long idPersona) {
        return this.personaRepository.obtenerPersonaPorId(idPersona);
    }

    @Override
    public TblPersona obtenerPersonaById(Long idPersona) {
        return this.personaRepository.findById(idPersona).orElse(null);
    }

    @Override
    @Transactional(readOnly = false)
    public TblPersonaDTO crearPersona(TblPersonaDTO tblPersonaDTO) throws Exception {

        TblPersona tblPersona = new TblPersona();

        tblPersona.setNoPersona(tblPersonaDTO.getNoPersona());
        tblPersona.setApPaterno(tblPersonaDTO.getApPaterno());
        tblPersona.setApMaterno(tblPersonaDTO.getApMaterno());

        tblPersona.setEsRegistro(ConstantesUtil.IND_ACTIVO);
        tblPersona.setFeCreacion(new Date());

        TblPersona tblPersonaCreado = this.personaRepository.save(tblPersona);

        TblPersonaDTO tblPersonaDTOCreado = this.personaRepository.obtenerPersonaPorId(tblPersonaCreado.getIdPersona());

        return tblPersonaDTOCreado;
    }

    @Override
    @Transactional(readOnly = false)
    public TblPersonaDTO modificarPersona(TblPersonaDTO tblPersonaDTO, TblPersona tblPersona) throws Exception {

        tblPersona.setNoPersona(tblPersonaDTO.getNoPersona());
        tblPersona.setApPaterno(tblPersonaDTO.getApPaterno());
        tblPersona.setApMaterno(tblPersonaDTO.getApMaterno());

        TblPersona tblPersonaModificado = this.personaRepository.save(tblPersona);

        TblPersonaDTO tblPersonaDTOResultado = this.personaRepository
                .obtenerPersonaPorId(tblPersonaModificado.getIdPersona());

        return tblPersonaDTOResultado;
    }

    @Transactional(readOnly = false)
    @Override
    public Long eliminarPersona(Long idPersona) throws Exception {

        Long rpta = 0L;
        TblPersona tblPersona = null;

        if (CommonsUtil.isNullOrZeroLong(idPersona)) {

            tblPersona = this.obtenerPersonaById(idPersona);

            if (tblPersona != null) {
                tblPersona.setEsRegistro(ConstantesUtil.IND_INACTIVO);
                this.personaRepository.save(tblPersona);
                rpta = tblPersona.getIdPersona();
            }
        }
        return rpta;
    }

    /*
     * -----------------------------------------------
     * ----------- CRUD de prestamo ------------------
     * -----------------------------------------------
     */
    @Override
    public Page<TblPrestamoDTO> paginarPrestamos(Long idPersona, Pageable paginador) {
        Page<TblPrestamoDTO> pTblPrestamoDTO = this.prestamoRepository.paginarPrestamos(idPersona, paginador);
        return pTblPrestamoDTO;
    }

    @Override
    public TblPrestamoDTO obtenerPrestamoPorId(Long idPrestamo) {
        return this.prestamoRepository.obtenerPrestamoPorId(idPrestamo);
    }

    @Override
    public TblPrestamo obtenerPrestamoById(Long idPrestamo) {
        return this.prestamoRepository.findById(idPrestamo).orElse(null);
    }

    @Override
    @Transactional(readOnly = false)
    public TblPrestamoDTO crearPrestamo(TblPrestamoDTO tblPrestamoDTO) throws Exception {

        TblPrestamo tblPrestamo = new TblPrestamo();

        tblPrestamo.setTblPersona(new TblPersona());
        tblPrestamo.getTblPersona().setIdPersona(tblPrestamoDTO.getIdPersona());
        tblPrestamo.setFePrestamo(tblPrestamoDTO.getFePrestamo());
        tblPrestamo.setFeCancelado(tblPrestamoDTO.getFeCancelado());
        tblPrestamo.setDescripcion(tblPrestamoDTO.getDescripcion());
        tblPrestamo.setMonto(tblPrestamoDTO.getMonto());
        tblPrestamo.setEstado("1");

        tblPrestamo.setEsRegistro(ConstantesUtil.IND_ACTIVO);
        tblPrestamo.setFeCreacion(new Date());

        TblPrestamo TblPrestamoCreado = this.prestamoRepository.save(tblPrestamo);

        TblPrestamoDTO tblPrestamoDTOCreado = this.prestamoRepository
                .obtenerPrestamoPorId(TblPrestamoCreado.getIdPrestamo());

        return tblPrestamoDTOCreado;
    }

    @Override
    @Transactional(readOnly = false)
    public TblPrestamoDTO modificarPrestamo(TblPrestamoDTO tblPrestamoDTO, TblPrestamo tblPrestamo) throws Exception {

        tblPrestamo.setTblPersona(new TblPersona());
        tblPrestamo.getTblPersona().setIdPersona(tblPrestamoDTO.getIdPersona());
        tblPrestamo.setFePrestamo(tblPrestamoDTO.getFePrestamo());
        tblPrestamo.setFeCancelado(tblPrestamoDTO.getFeCancelado());
        tblPrestamo.setDescripcion(tblPrestamoDTO.getDescripcion());
        tblPrestamo.setMonto(tblPrestamoDTO.getMonto());
        tblPrestamo.setEstado(tblPrestamoDTO.getEstado()); 

        TblPrestamo tblPrestamoModificado = this.prestamoRepository.save(tblPrestamo);

        TblPrestamoDTO tblPrestamoDTOResultado = this.prestamoRepository
                .obtenerPrestamoPorId(tblPrestamoModificado.getIdPrestamo());

        BigDecimal deudaTotal = this.prestamoRepository.calcularDeudaTotal(tblPrestamoDTO.getIdPersona());

        if(deudaTotal == null){
            // Procedemos a actualizar el estado de la deuda a cancelado = 1
            TblPersona tblPersona = this.obtenerPersonaById(tblPrestamoDTO.getIdPersona());

            tblPersona.setFlEstadoDeuda("1");

            this.personaRepository.save(tblPersona);

        }else{
            // Procedemos a actualizar el estado de la deuda a pendiente = 0
            TblPersona tblPersona = this.obtenerPersonaById(tblPrestamoDTO.getIdPersona());

            tblPersona.setFlEstadoDeuda("0");

            this.personaRepository.save(tblPersona);
        }
        
        return tblPrestamoDTOResultado;
    }

    @Override
    @Transactional(readOnly = false)
    public Long eliminarPrestamo(Long idPrestamo) throws Exception {

        Long rpta = 0L;
        TblPrestamo tblPrestamo = null;

        if (CommonsUtil.isNullOrZeroLong(idPrestamo)) {

            tblPrestamo = this.obtenerPrestamoById(idPrestamo);

            if (tblPrestamo != null) {
                tblPrestamo.setEsRegistro(ConstantesUtil.IND_INACTIVO);
                this.prestamoRepository.save(tblPrestamo);
                rpta = tblPrestamo.getIdPrestamo();
            }
        }
        return rpta;
    }

    /*
     * -----------------------------------------------
     * ----------- CRUD de historial prestamo --------
     * -----------------------------------------------
     */
    @Override
    public Page<TblHistorialPrestamoDTO> paginarHistorialPrestamos(Long idPrestamo, Pageable paginador) {
        Page<TblHistorialPrestamoDTO> pTblHistorialPrestamoDTO = this.historialPrestamoRepository
                .paginarHistorialPrestamos(idPrestamo, paginador);
        return pTblHistorialPrestamoDTO;
    }

    @Override
    public TblHistorialPrestamoDTO obtenerHistorialPrestamoPorId(Long idHistorialPrestamo) {
        return this.historialPrestamoRepository.obtenerHistorialPrestamoPorId(idHistorialPrestamo);
    }

    @Override
    public TblHistorialPrestamo obtenerHistorialPrestamoById(Long idHistorialPrestamo) {
        return this.historialPrestamoRepository.findById(idHistorialPrestamo).orElse(null);
    }

    @Override
    @Transactional(readOnly = false)
    public TblHistorialPrestamoDTO crearHistorialPrestamo(TblHistorialPrestamoDTO tblHistorialPrestamoDTO)
            throws Exception {

        TblHistorialPrestamo tblHistorialPrestamo = new TblHistorialPrestamo();

        tblHistorialPrestamo.setTblPrestamo(new TblPrestamo());
        tblHistorialPrestamo.getTblPrestamo().setIdPrestamo(tblHistorialPrestamoDTO.getIdPrestamo());
        tblHistorialPrestamo.setFePrestamoHistorial(new Date());
        tblHistorialPrestamo.setDescripcionHistorial(tblHistorialPrestamoDTO.getDescripcionHistorial()); //tblHistorialPrestamoDTO.getDescripcionHistorial()
        tblHistorialPrestamo.setMontoSubtotalOperado(tblHistorialPrestamoDTO.getMontoSubtotalOperado());
        tblHistorialPrestamo.setMontoOperar(tblHistorialPrestamoDTO.getMontoOperar());
        tblHistorialPrestamo.setFlagOperador(tblHistorialPrestamoDTO.getFlagOperador()); // tblHistorialPrestamoDTO.getFlagOperador() // suma = '1' | resta = '0'

        tblHistorialPrestamo.setEsRegistro(ConstantesUtil.IND_ACTIVO);

        TblHistorialPrestamo TblHistoriaPrestamoCreado = this.historialPrestamoRepository.save(tblHistorialPrestamo);

        TblHistorialPrestamoDTO tblHistorialPrestamoDTOCreado = this.historialPrestamoRepository
                .obtenerHistorialPrestamoPorId(TblHistoriaPrestamoCreado.getIdHistorialPrestamo());

        return tblHistorialPrestamoDTOCreado;
    }

    @Override
    @Transactional(readOnly = false)
    public TblHistorialPrestamoDTO modificarHistorialPrestamo(TblHistorialPrestamoDTO tblHistorialPrestamoDTO,
            TblHistorialPrestamo tblHistorialPrestamo) throws Exception {

        tblHistorialPrestamo.setTblPrestamo(new TblPrestamo());
        tblHistorialPrestamo.getTblPrestamo().setIdPrestamo(tblHistorialPrestamoDTO.getIdPrestamo());
        tblHistorialPrestamo.setFePrestamoHistorial(new Date());
        tblHistorialPrestamo.setDescripcionHistorial(tblHistorialPrestamoDTO.getDescripcionHistorial());
        tblHistorialPrestamo.setMontoSubtotalOperado(tblHistorialPrestamoDTO.getMontoSubtotalOperado());
        tblHistorialPrestamo.setMontoOperar(tblHistorialPrestamoDTO.getMontoOperar());
        tblHistorialPrestamo.setFlagOperador(tblHistorialPrestamoDTO.getFlagOperador());

        TblHistorialPrestamo tblHistorialPrestamoModificado = this.historialPrestamoRepository.save(tblHistorialPrestamo);

        TblHistorialPrestamoDTO tblHistorialPrestamoDTOResultado = this.historialPrestamoRepository
                .obtenerHistorialPrestamoPorId(tblHistorialPrestamoModificado.getIdHistorialPrestamo());

        return tblHistorialPrestamoDTOResultado;
    }

    @Override
    @Transactional(readOnly = false)
    public Long eliminarHistorialPrestamo(Long idHistorialPrestamo) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarHistorialPrestamo'");
    }
}
