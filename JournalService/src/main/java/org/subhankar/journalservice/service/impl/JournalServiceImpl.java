package org.subhankar.journalservice.service.impl;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.subhankar.journalservice.model.DO.JournalDO;
import org.subhankar.journalservice.model.DTO.FilterJournalDTO;
import org.subhankar.journalservice.model.DTO.FilterJournalResponseDTO;
import org.subhankar.journalservice.model.DTO.ResponseDTO;
import org.subhankar.journalservice.repository.JournalRepository;
import org.subhankar.journalservice.service.JournalService;

import java.awt.print.Pageable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
        String userId = filterJournalDTO.getUserId();
        String message = filterJournalDTO.getMessage();
        String createdAt = filterJournalDTO.getCreatedDate();
        String startDate = filterJournalDTO.getStartDate();
        String endDate = filterJournalDTO.getEndDate();

        String role = filterJournalDTO.getRole();
        if(role != null || !role.isEmpty() || role != "" || !role.isBlank()){
            role = role.trim().toUpperCase();
        }
        int pageNo = filterJournalDTO.getPageNumber()==0?0:filterJournalDTO.getPageNumber()-1;
        int pageSize = filterJournalDTO.getPageSize()==0?10:filterJournalDTO.getPageSize();
        String sortBy = filterJournalDTO.getSortBy()==null||filterJournalDTO.getSortBy().isEmpty()?"createdAt":filterJournalDTO.getSortBy();
        String sortOrder = filterJournalDTO.getSortOrder()==null||filterJournalDTO.getSortOrder().isEmpty()? "DESC":filterJournalDTO.getSortOrder();

        Sort sort = Sort.by(sortOrder.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

        LocalDateTime createdDate = null;
        LocalDateTime start = null;
        LocalDateTime end = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        if (createdAt != null && !createdAt.isEmpty()) {
            if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
                throw new RuntimeException("Cannot filter by both createdAt and startDate/endDate");

            }
                // Convert createdAt to Date
            createdDate = LocalDateTime.parse(createdAt,formatter);
        }
        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            // Convert startDate and endDate to Date
            if (createdAt != null && !createdAt.isEmpty()) {
                throw new RuntimeException("Cannot filter by both createdAt and startDate/endDate");
            }
            // Define custom DateTimeFormatter for the given format

            try {
                // Parse the date-time string using the custom formatter
                start = LocalDateTime.parse(startDate, formatter);
                end = LocalDateTime.parse(endDate, formatter);
            } catch (DateTimeParseException e) {
                // Handle parsing exception
                e.printStackTrace();
            }

        }

        Page<JournalDO> journalDOS = journalRepository.filter(pageRequest, userId, message, createdDate, start, end, role);
        FilterJournalResponseDTO filterJournalResponseDTO = FilterJournalResponseDTO.builder()
                .journalDOList(journalDOS.getContent())
                .totalPages(journalDOS.getTotalPages())
                .totalElements(journalDOS.getTotalElements())
                .currentPage(journalDOS.getNumber())
                .pageSize(journalDOS.getSize())
                .build();
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status("200")
                .message("Journals Fetched successfully")
                .data(filterJournalResponseDTO)
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<ResponseDTO> deleteAllJournals() {
        journalRepository.deleteAll();
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status("200")
                .message("All Journals Deleted Successfully")
                .build();
        return ResponseEntity.ok(responseDTO);
    }


}
