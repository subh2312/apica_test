package org.subhankar.journalservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.subhankar.journalservice.model.DO.JournalDO;
import org.subhankar.journalservice.model.DTO.FilterJournalDTO;
import org.subhankar.journalservice.model.DTO.ResponseDTO;
import org.subhankar.journalservice.repository.JournalRepository;
import org.subhankar.journalservice.service.JournalService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService {
    private final JournalRepository journalRepository;

    @Override
    public ResponseEntity<ResponseDTO> getJournalById(String id) {
        JournalDO journalDO = journalRepository.findById(id).orElseThrow(()->new RuntimeException("Journal not found"));
        ResponseDTO responseDTO = ResponseDTO.builder().message("Journal Found Successfully").status("200").data(journalDO).build();
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<ResponseDTO> getAllJournals() {
        List<JournalDO> journalDOS = journalRepository.findAll();
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status("200")
                .message("Journals Fetched successfully")
                .data(journalDOS)
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<ResponseDTO> filterJournals(FilterJournalDTO filterJournalDTO) {

        return null;
    }
}
