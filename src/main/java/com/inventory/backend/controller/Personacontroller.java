package com.inventory.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.backend.dtos.ResponseDTO;
import com.inventory.backend.dtos.TblHistorialPrestamoDTO;
import com.inventory.backend.dtos.TblPersonaDTO;
import com.inventory.backend.dtos.TblPrestamoDTO;
import com.inventory.backend.exception.PgimException.TipoResultado;
import com.inventory.backend.model.TblPersona;
import com.inventory.backend.model.TblPrestamo;
import com.inventory.backend.services.PersonaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/personas")
public class Personacontroller {

    @Autowired
    private PersonaService personaService;

    @PostMapping("/paginarPersonas")
    public ResponseEntity<Page<TblPersonaDTO>> paginarPersonas(final Pageable paginador) throws Exception {

        final Page<TblPersonaDTO> pTblPersonaDTO = this.personaService.paginarPersonas(paginador);
        return new ResponseEntity<Page<TblPersonaDTO>>(pTblPersonaDTO, HttpStatus.OK);
    }

    @PostMapping("/crearPersona")
    public ResponseEntity<?> crearPersona(@RequestBody TblPersonaDTO tblPersonaDTO, BindingResult resultadoValidacion)
            throws Exception {

        TblPersonaDTO tblPersonaDTOCreado = null;
        Map<String, Object> respuesta = new HashMap<>();

        if (resultadoValidacion.hasErrors()) {
            List<String> errores = null;

            errores = resultadoValidacion.getFieldErrors().stream()
                    .map(err -> String.format("La propiedad '%s' %s", err.getField(), err.getDefaultMessage()))
                    .collect(Collectors.toList());

            respuesta.put("mensaje", "Se han encontrado inconsistencias para crear la persona");
            respuesta.put("error", errores.toString());

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
        }

        try {
            tblPersonaDTOCreado = this.personaService.crearPersona(tblPersonaDTO);

        } catch (DataAccessException e) {
            respuesta.put("mensaje", "Ocurrió un error al intentar crear la persona");
            respuesta.put("error", e.getMostSpecificCause().getMessage());
            log.error(e.getMostSpecificCause().getMessage(), e);

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        respuesta.put("mensaje", "La persona ha sido creada");
        respuesta.put("tblPersonaDTOCreado", tblPersonaDTOCreado);

        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/modificarPersona")
    public ResponseEntity<?> modificarPersona(@RequestBody TblPersonaDTO tblPersonaDTO,
            BindingResult resultadoValidacion) throws Exception {

        TblPersona tblPersonaActual = null;
        TblPersonaDTO tblPersonaDTOModificado = null;
        String mensaje;
        Map<String, Object> respuesta = new HashMap<>();

        if (resultadoValidacion.hasErrors()) {
            List<String> errores = null;

            errores = resultadoValidacion.getFieldErrors().stream()
                    .map(err -> String.format("La propiedad '%s' %s", err.getField(), err.getDefaultMessage()))
                    .collect(Collectors.toList());

            respuesta.put("mensaje", "Se han encontrado inconsistencias para modificar la persona");
            respuesta.put("error", errores.toString());
            log.error("Error al modificar la persona: " + errores.toString());

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
        }

        try {
            tblPersonaActual = this.personaService.obtenerPersonaById(tblPersonaDTO.getIdPersona());

            if (tblPersonaActual == null) {
                mensaje = String.format("La persona %s que intenta actualizar no existe en la base de datos",
                        tblPersonaDTO.getIdPersona());
                respuesta.put("mensaje", mensaje);

                return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            respuesta.put("mensaje", "Ocurrió un error al intentar recuperar la persona a actualizar");
            respuesta.put("error", e.getMostSpecificCause().getMessage());
            log.error(e.getMostSpecificCause().getMessage(), e);

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            tblPersonaDTOModificado = this.personaService.modificarPersona(tblPersonaDTO, tblPersonaActual);

        } catch (DataAccessException e) {
            respuesta.put("mensaje", "Ocurrió un error al intentar modificar la persona");
            respuesta.put("error", e.getMostSpecificCause().getMessage());
            log.error(e.getMostSpecificCause().getMessage(), e);

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        respuesta.put("mensaje", "la persona ha sido modificado");
        respuesta.put("tblPersonaDTOModificado", tblPersonaDTOModificado);

        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.CREATED);
    }

    @PostMapping("/eliminarPersona/{idPersona}")
    public ResponseEntity<ResponseDTO> eliminarPersona(@PathVariable("idPersona") Long idPersona) throws Exception {

        try {

            Long rpta = this.personaService.eliminarPersona(idPersona);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(TipoResultado.SUCCESS, "La persona ha sido eliminado correctamente", rpta));

        } catch (DataAccessException e) {

            log.error(e.getMostSpecificCause().getMessage(), e);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(TipoResultado.ERROR,
                    "Error al intentar eliminar la persona: ", e.getMostSpecificCause().getMessage()));

        }
    }

    @GetMapping("/obtenerPersonaPorId/{idPersona}")
    public ResponseEntity<?> obtenerPersonaPorId(@PathVariable Long idPersona) {
        String mensaje;
        Map<String, Object> respuesta = new HashMap<>();

        TblPersonaDTO tblPersonaDTO = null;

        try {
            tblPersonaDTO = this.personaService.obtenerPersonaPorId(idPersona);
        } catch (DataAccessException e) {
            respuesta.put("mensaje", "Ocurrió un error al intentar recuperar la persona");
            respuesta.put("error", e.getMostSpecificCause().getMessage());

            log.error(e.getMostSpecificCause().getMessage(), e);

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            respuesta.put("mensaje", "Ocurrió un error al intentar recuperar la persona");
            respuesta.put("error", e.getMessage());

            log.error(e.getMessage(), e);

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (tblPersonaDTO == null) {
            mensaje = String.format("La persona con el id: %d no existe en la base de datos",
                    idPersona);
            respuesta.put("mensaje", mensaje);

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NO_CONTENT);
        }

        respuesta.put("mensaje", "La persona ha sido recuperado");
        respuesta.put("tblPersonaDTO", tblPersonaDTO);

        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/obtenerPrestamoPorId/{idPrestamo}")
    public ResponseEntity<?> obtenerPrestamoPorId(@PathVariable Long idPrestamo) {
        String mensaje;
        Map<String, Object> respuesta = new HashMap<>();

        TblPrestamoDTO tblPrestamoDTO = null;

        try {
            tblPrestamoDTO = this.personaService.obtenerPrestamoPorId(idPrestamo);
        } catch (DataAccessException e) {
            respuesta.put("mensaje", "Ocurrió un error al intentar recuperar el prestamo");
            respuesta.put("error", e.getMostSpecificCause().getMessage());

            log.error(e.getMostSpecificCause().getMessage(), e);

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            respuesta.put("mensaje", "Ocurrió un error al intentar recuperar el prestamo");
            respuesta.put("error", e.getMessage());

            log.error(e.getMessage(), e);

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (tblPrestamoDTO == null) {
            mensaje = String.format("el prestamo con el id: %d no existe en la base de datos",
                    idPrestamo);
            respuesta.put("mensaje", mensaje);

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NO_CONTENT);
        }

        respuesta.put("mensaje", "el prestamo ha sido recuperado");
        respuesta.put("tblPrestamoDTO", tblPrestamoDTO);

        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/paginarPrestamos/{idPersona}")
    public ResponseEntity<ResponseDTO> paginarPrestamos(@RequestBody final Long idPersona, final Pageable paginador)
            throws Exception {

        Map<String, Object> respuesta = new HashMap<>();
        ResponseDTO responseDTO = null;
        int pageNo = 0;
        int totalElements = 1;

        Page<TblPrestamoDTO> pTblPrestamoDTO = null;
        Page<TblPrestamoDTO> pTblPrestamoDTOTotal = null;
        
        try {
            pTblPrestamoDTO = this.personaService.paginarPrestamos(idPersona, paginador);
        } catch (DataAccessException e) {
            log.error(e.getMostSpecificCause().getMessage(), e);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(TipoResultado.ERROR, "Ocurrió un error al intentar listar los prestamos: " + e.getMostSpecificCause().getMessage(), 0));
        }

        Long pageSize = (Long) pTblPrestamoDTO.getTotalElements();

        try {
            if (pageSize > 0) {
                totalElements = pageSize.intValue();
            }
            Pageable paging = PageRequest.of(pageNo, totalElements); 

            pTblPrestamoDTOTotal = this.personaService.paginarPrestamos(idPersona, paging);

        } catch (DataAccessException e) {
            log.error(e.getMostSpecificCause().getMessage(), e);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(TipoResultado.ERROR, "Ocurrió un error al intentar listar los prestamos en general: " + e.getMostSpecificCause().getMessage(), 0));
        }

        respuesta.put("pTblPrestamoDTO", pTblPrestamoDTO);
        respuesta.put("pTblPrestamoDTOTotal", pTblPrestamoDTOTotal);

        responseDTO = new ResponseDTO(TipoResultado.SUCCESS, respuesta, "Estupendo, la lista de prestamos ha sido exitosa");

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/crearPrestamo")
    public ResponseEntity<?> crearPrestamo(@RequestBody TblPrestamoDTO tblPrestamoDTO,
            BindingResult resultadoValidacion) throws Exception {

        TblPrestamoDTO tblPrestamoDTOCreado = null;
        Map<String, Object> respuesta = new HashMap<>();

        if (resultadoValidacion.hasErrors()) {
            List<String> errores = null;

            errores = resultadoValidacion.getFieldErrors().stream()
                    .map(err -> String.format("La propiedad '%s' %s", err.getField(), err.getDefaultMessage()))
                    .collect(Collectors.toList());

            respuesta.put("mensaje", "Se han encontrado inconsistencias para crear el prestamo");
            respuesta.put("error", errores.toString());

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
        }

        try {
            tblPrestamoDTOCreado = this.personaService.crearPrestamo(tblPrestamoDTO);

        } catch (DataAccessException e) {
            respuesta.put("mensaje", "Ocurrió un error al intentar crear el prestamo");
            respuesta.put("error", e.getMostSpecificCause().getMessage());
            log.error(e.getMostSpecificCause().getMessage(), e);

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        respuesta.put("mensaje", "El prestamo ha sido creada");
        respuesta.put("tblPrestamoDTOCreado", tblPrestamoDTOCreado);

        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/modificarPrestamo")
    public ResponseEntity<?> modificarPrestamo(@RequestBody TblPrestamoDTO tblPrestamoDTO,
            BindingResult resultadoValidacion) throws Exception {

        TblPrestamo tblPrestamoActual = null;
        TblPrestamoDTO tblPrestamoDTOModificado = null;
        String mensaje;
        Map<String, Object> respuesta = new HashMap<>();

        if (resultadoValidacion.hasErrors()) {
            List<String> errores = null;

            errores = resultadoValidacion.getFieldErrors().stream()
                    .map(err -> String.format("La propiedad '%s' %s", err.getField(), err.getDefaultMessage()))
                    .collect(Collectors.toList());

            respuesta.put("mensaje", "Se han encontrado inconsistencias para modificar el prestamo");
            respuesta.put("error", errores.toString());
            log.error("Error al modificar el prestamo: " + errores.toString());

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
        }

        try {
            tblPrestamoActual = this.personaService.obtenerPrestamoById(tblPrestamoDTO.getIdPrestamo());

            if (tblPrestamoActual == null) {
                mensaje = String.format("el prestamo %s que intenta actualizar no existe en la base de datos",
                        tblPrestamoDTO.getIdPrestamo());
                respuesta.put("mensaje", mensaje);

                return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            respuesta.put("mensaje", "Ocurrió un error al intentar recuperar el prestamo a actualizar");
            respuesta.put("error", e.getMostSpecificCause().getMessage());
            log.error(e.getMostSpecificCause().getMessage(), e);

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            tblPrestamoDTOModificado = this.personaService.modificarPrestamo(tblPrestamoDTO, tblPrestamoActual);

        } catch (DataAccessException e) {
            respuesta.put("mensaje", "Ocurrió un error al intentar modificar el prestamo");
            respuesta.put("error", e.getMostSpecificCause().getMessage());
            log.error(e.getMostSpecificCause().getMessage(), e);

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        respuesta.put("mensaje", "el prestamo ha sido modificado");
        respuesta.put("tblPrestamoDTOModificado", tblPrestamoDTOModificado);

        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.CREATED);
    }

    @PostMapping("/eliminarPrestamo/{idPrestamo}")
    public ResponseEntity<ResponseDTO> eliminarPrestamo(@PathVariable("idPrestamo") Long idPrestamo) throws Exception {

        try {

            Long rpta = this.personaService.eliminarPrestamo(idPrestamo);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(TipoResultado.SUCCESS, "el prestamo ha sido eliminado correctamente", rpta));

        } catch (DataAccessException e) {

            log.error(e.getMostSpecificCause().getMessage(), e);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(TipoResultado.ERROR,
                    "Error al intentar eliminar el prestamo: ", e.getMostSpecificCause().getMessage()));

        }
    }

    @PostMapping("/paginarHistorialPrestamos")
    public ResponseEntity<ResponseDTO> paginarHistorialPrestamos(@RequestBody final Long idPrestamo, final Pageable paginador)
            throws Exception {

        ResponseDTO responseDTO = null;
        Page<TblHistorialPrestamoDTO> pTblHistorialPrestamoDTO = null;
        
        try {
            pTblHistorialPrestamoDTO = this.personaService.paginarHistorialPrestamos(idPrestamo, paginador);
        } catch (DataAccessException e) {
            log.error(e.getMostSpecificCause().getMessage(), e);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(TipoResultado.ERROR, "Ocurrió un error al intentar listar el historial de los prestamos: " + e.getMostSpecificCause().getMessage(), 0));
        }

        responseDTO = new ResponseDTO(TipoResultado.SUCCESS, pTblHistorialPrestamoDTO, "Estupendo, la lista de historial de prestamos ha sido exitosa");

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/crearHistorialPrestamo")
    public ResponseEntity<ResponseDTO> crearHistorialPrestamo(@RequestBody TblHistorialPrestamoDTO tblHistorialPrestamoDTO,
            BindingResult resultadoValidacion) throws Exception {

        TblHistorialPrestamoDTO tblHistorialPrestamoDTOCreado = null;
        ResponseDTO responseDTO = null;

        if (resultadoValidacion.hasErrors()) {
            List<String> errores = null;

            errores = resultadoValidacion.getFieldErrors().stream()
                    .map(err -> String.format("La propiedad '%s' %s", err.getField(), err.getDefaultMessage()))
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(TipoResultado.ERROR, "Se han encontrado inconsistencias para crear el prestamo" + errores.toString()));
        }

        try {
            tblHistorialPrestamoDTOCreado = this.personaService.crearHistorialPrestamo(tblHistorialPrestamoDTO);
        } catch (DataAccessException e) {
            log.error(e.getMostSpecificCause().getMessage(), e);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(TipoResultado.ERROR, "Ocurrió un error al intentar crear el prestamo: " + e.getMostSpecificCause().getMessage()));
        }

        responseDTO = new ResponseDTO(TipoResultado.SUCCESS, tblHistorialPrestamoDTOCreado, "El historial del prestamo ha sido creada");

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
