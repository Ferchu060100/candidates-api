package com.farevalo.candidates_api_seek.domain.service.Impl;

import ch.qos.logback.classic.Logger;
import com.farevalo.candidates_api_seek.domain.model.Candidate;
import com.farevalo.candidates_api_seek.domain.model.dto.CandidateCreateDTO;
import com.farevalo.candidates_api_seek.domain.model.dto.CandidateUpdateDTO;
import com.farevalo.candidates_api_seek.domain.service.CandidateService;
import com.farevalo.candidates_api_seek.infraestructure.repository.CandidateRepository;
import com.farevalo.candidates_api_seek.shared.exception.DatabaseOperationException;
import com.farevalo.candidates_api_seek.shared.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate getCandidateById(Long id) {

        log.debug("Buscando candidato con id: {}", id);

        if (id == null) {
            throw new IllegalArgumentException("El ID del candidato no puede ser nulo");
        }

        return candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No se encontró el candidato con ID: %d", id)
                ));
    }

    @Override
    public Candidate createCandidate(CandidateCreateDTO candidateDTO) {
        if (candidateRepository.existsByEmail(candidateDTO.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Candidate candidate = new Candidate();
        candidate.setName(candidateDTO.getName());
        candidate.setEmail(candidateDTO.getEmail());
        candidate.setGender(candidateDTO.getGender());
        candidate.setSalaryExpected(candidateDTO.getSalaryExpected());

        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate updateCandidate(Long id, CandidateUpdateDTO candidateDTO) {
        log.debug("Actualizando candidato con id: {}", id);

        // Validar que el id no sea nulo
        if (id == null) {
            throw new IllegalArgumentException("El ID del candidato no puede ser nulo");
        }

        // Buscar el candidato existente
        Candidate existingCandidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No se encontró el candidato con ID: %d", id)
                ));

        // Validar el email si ha cambiado
        if (candidateDTO.getEmail() != null &&
                !candidateDTO.getEmail().equals(existingCandidate.getEmail())) {
            validateEmail(candidateDTO.getEmail(),id);
        }

        // Actualizar solo los campos no nulos
        if (candidateDTO.getName() != null) {
            existingCandidate.setName(candidateDTO.getName());
        }
        if (candidateDTO.getEmail() != null) {
            existingCandidate.setEmail(candidateDTO.getEmail());
        }
        if (candidateDTO.getGender() != null) {
            existingCandidate.setGender(candidateDTO.getGender());
        }
        if (candidateDTO.getSalaryExpected() != null) {
            existingCandidate.setSalaryExpected(candidateDTO.getSalaryExpected());
        }

        // Guardar y retornar el candidato actualizado
        return candidateRepository.save(existingCandidate);
    }

    private void validateEmail(String email, Long id) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email inválido");
        }
        // Verificar si el email ya está en uso por otro candidato
        if (candidateRepository.existsByEmailAndIdNot(email, id)) {
            throw new IllegalArgumentException("El email ya está en uso");
        }
    }

    @Override
    public void deleteCandidate(Long id) {
        // Primero verificamos si el candidato existe
        if (!candidateRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró el candidato con ID: " + id);
        }

        try {
            candidateRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("No se puede eliminar el candidato debido a restricciones de integridad");
        } catch (Exception e) {
            throw new DatabaseOperationException("Error al eliminar el candidato: " + e.getMessage());
        }
    }
}

