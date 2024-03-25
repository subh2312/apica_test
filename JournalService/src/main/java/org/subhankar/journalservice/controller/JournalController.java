package org.subhankar.journalservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.subhankar.journalservice.model.DTO.FilterJournalDTO;
import org.subhankar.journalservice.model.DTO.ResponseDTO;
import org.subhankar.journalservice.service.JournalService;

@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalController {
    private final JournalService journalService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getJournalById(@PathVariable String id) {
        return journalService.getJournalById(id);
    }

    @GetMapping()
    public ResponseEntity<ResponseDTO> getAllJournals() {
        return journalService.getAllJournals();
    }


    @PostMapping("/filter")
    public ResponseEntity<ResponseDTO> filterJournals(@RequestBody FilterJournalDTO filterJournalDTO) {
        return journalService.filterJournals(filterJournalDTO);
    }

    @DeleteMapping()
    public ResponseEntity<ResponseDTO> deleteAllJournals() {
        return journalService.deleteAllJournals();
    }
}
