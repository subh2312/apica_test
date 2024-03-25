package org.subhankar.journalservice.service;

import org.springframework.http.ResponseEntity;
import org.subhankar.journalservice.model.DTO.FilterJournalDTO;
import org.subhankar.journalservice.model.DTO.ResponseDTO;

public interface JournalService {
    ResponseEntity<ResponseDTO> getJournalById(String id);

    ResponseEntity<ResponseDTO> getAllJournals();

    ResponseEntity<ResponseDTO> filterJournals(FilterJournalDTO filterJournalDTO);
}
