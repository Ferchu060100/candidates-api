package com.farevalo.candidates_api_seek.domain.service;

import com.farevalo.candidates_api_seek.domain.model.Candidate;
import com.farevalo.candidates_api_seek.domain.model.dto.CandidateCreateDTO;
import com.farevalo.candidates_api_seek.domain.model.dto.CandidateUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface CandidateService {
    List<Candidate> getAllCandidates();
    Candidate getCandidateById(Long id);
    Candidate createCandidate(CandidateCreateDTO candidate);
    Candidate updateCandidate(Long id, CandidateUpdateDTO candidate);
    void deleteCandidate(Long id);
}
