package com.farevalo.candidates_api_seek;

import com.farevalo.candidates_api_seek.domain.model.Candidate;
import com.farevalo.candidates_api_seek.domain.model.dto.CandidateCreateDTO;
import com.farevalo.candidates_api_seek.domain.model.dto.CandidateUpdateDTO;
import com.farevalo.candidates_api_seek.domain.service.Impl.CandidateServiceImpl;
import com.farevalo.candidates_api_seek.infraestructure.repository.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)  // Agrega esto para usar Mockito con JUnit 5
public class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private CandidateServiceImpl candidateService;

    private Candidate candidate;

    @BeforeEach
    public void setup() {
        candidate = new Candidate();
        candidate.setId(1L);
        candidate.setName("Juan Pérez");
        candidate.setEmail("juan.perez@example.com");
        candidate.setGender("Male");
        candidate.setSalaryExpected(3000.00);
    }

    @Test
    public void testGetCandidateById() {
        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));

        Candidate foundCandidate = candidateService.getCandidateById(1L);

        assertNotNull(foundCandidate);
        assertEquals("Juan Pérez", foundCandidate.getName());
        verify(candidateRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateCandidate() {
        // Datos simulados para el DTO y la entidad resultante
        Candidate candidate = new Candidate(1L, "Juan Pérez", "juan.perez@example.com", "MASCULINO", 5000.0);
        CandidateCreateDTO candidateDTO = new CandidateCreateDTO(
                "Juan Pérez",
                "juan.perez@example.com",
                "MASCULINO",
                5000.0
        );

        // Configuración del mock para el repositorio
        when(candidateRepository.save(any(Candidate.class))).thenReturn(candidate);

        // Ejecución del servicio
        Candidate savedCandidate = candidateService.createCandidate(candidateDTO);

        // Verificaciones
        assertNotNull(savedCandidate, "El candidato guardado no debería ser nulo");
        assertEquals("Juan Pérez", savedCandidate.getName(), "El nombre debería coincidir");
        assertEquals("juan.perez@example.com", savedCandidate.getEmail(), "El email debería coincidir");
        assertEquals("MASCULINO", savedCandidate.getGender(), "El género debería coincidir");
        assertEquals(5000.0, savedCandidate.getSalaryExpected(), "El salario esperado debería coincidir");

        // Verificar interacción con el repositorio
        verify(candidateRepository, times(1)).save(any(Candidate.class));
    }

    @Test
    public void testDeleteCandidate() {
        // Simula que el candidato con ID 1 existe
        when(candidateRepository.existsById(1L)).thenReturn(true);

        // Simula que no se produce ninguna acción cuando se llama a deleteById
        doNothing().when(candidateRepository).deleteById(1L);

        // Llamada al servicio
        candidateService.deleteCandidate(1L);

        // Verificación de que deleteById fue llamado una vez
        verify(candidateRepository, times(1)).deleteById(1L);
    }
}
