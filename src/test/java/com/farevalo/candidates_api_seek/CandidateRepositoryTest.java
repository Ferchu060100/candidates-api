package com.farevalo.candidates_api_seek;

import com.farevalo.candidates_api_seek.domain.model.Candidate;
import com.farevalo.candidates_api_seek.domain.service.Impl.CandidateServiceImpl;
import com.farevalo.candidates_api_seek.infraestructure.repository.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CandidateRepositoryTest {

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
    public void testFindById() {
        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));

        Optional<Candidate> found = candidateRepository.findById(1L);

        assertTrue(found.isPresent());
        assertEquals("Juan Pérez", found.get().getName());
        verify(candidateRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveCandidate() {
        when(candidateRepository.save(any(Candidate.class))).thenReturn(candidate);

        Candidate savedCandidate = candidateRepository.save(candidate);

        assertNotNull(savedCandidate);
        assertEquals("Juan Pérez", savedCandidate.getName());
        verify(candidateRepository, times(1)).save(any(Candidate.class));
    }
}