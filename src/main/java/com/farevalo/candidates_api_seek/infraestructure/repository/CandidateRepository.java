package com.farevalo.candidates_api_seek.infraestructure.repository;

import com.farevalo.candidates_api_seek.domain.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByEmail(String email);
}