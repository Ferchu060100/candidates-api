package com.farevalo.candidates_api_seek.application.controller;

import com.farevalo.candidates_api_seek.domain.model.Candidate;
import com.farevalo.candidates_api_seek.domain.model.dto.CustomApiResponse;
import com.farevalo.candidates_api_seek.domain.model.dto.CandidateCreateDTO;
import com.farevalo.candidates_api_seek.domain.model.dto.CandidateUpdateDTO;
import com.farevalo.candidates_api_seek.domain.service.CandidateService;
import com.farevalo.candidates_api_seek.shared.exception.BusinessValidationException;
import com.farevalo.candidates_api_seek.shared.exception.DatabaseOperationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.farevalo.candidates_api_seek.shared.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/candidates")
@Tag(name = "Candidate API", description = "Gestión de candidatos")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Operation(summary = "Obtener todos los candidatos", description = "Devuelve una lista de todos los candidatos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de candidatos recuperada exitosamente")
    })
    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @Operation(summary = "Obtener un candidato por ID", description = "Devuelve un candidato basado en su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidato recuperado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Candidato no encontrado")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCandidateById(@PathVariable Long id) {
        try {
            Candidate candidate = candidateService.getCandidateById(id);
            return ResponseEntity.ok(candidate);
        } catch (ResourceNotFoundException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            log.error("Error al obtener candidato con id {}: {}", id, e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error interno al procesar la solicitud");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @Operation(summary = "Crear un nuevo candidato", description = "Registra un nuevo candidato en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Candidato creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos del candidato")
    })
    @PostMapping("/create")
    public ResponseEntity<Object> createCandidate(@Valid @RequestBody CandidateCreateDTO candidate) {
        try {
            Candidate createdCandidate = candidateService.createCandidate(candidate);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCandidate);
        } catch (ConstraintViolationException ex) {
            Map<String, String> errors = ex.getConstraintViolations().stream()
                    .collect(Collectors.toMap(
                            violation -> violation.getPropertyPath().toString(),
                            ConstraintViolation::getMessage
                    ));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        } catch (Exception e) {
            log.error("Error al crear candidato: {}", e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error interno al procesar la solicitud:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Actualizar un candidato", description = "Actualiza la información de un candidato basado en su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidato actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Candidato no encontrado")
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateCandidate(
            @PathVariable Long id,
            @Valid @RequestBody CandidateUpdateDTO candidateDTO) {
        try {
            Candidate updatedCandidate = candidateService.updateCandidate(id, candidateDTO);
            return ResponseEntity.ok(updatedCandidate);

        } catch (ResourceNotFoundException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {
            log.error("Error al actualizar candidato con id {}: {}", id, e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error interno al procesar la actualización");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Eliminar un candidato", description = "Elimina un candidato basado en su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidato eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Candidato no encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflicto al eliminar candidato")
    })
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomApiResponse> deleteCandidate(@PathVariable Long id) {
        try {
            candidateService.deleteCandidate(id);
            return ResponseEntity.ok(new CustomApiResponse("Candidato eliminado exitosamente"));
        } catch (ResourceNotFoundException e) {
            log.warn("Intento de eliminar candidato inexistente: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomApiResponse(e.getMessage()));
        } catch (BusinessValidationException e) {
            log.warn("Validación fallida al eliminar candidato {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CustomApiResponse(e.getMessage()));
        } catch (DatabaseOperationException e) {
            log.error("Error de base de datos al eliminar candidato {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomApiResponse("Error interno al procesar la solicitud"));
        }
    }
}
