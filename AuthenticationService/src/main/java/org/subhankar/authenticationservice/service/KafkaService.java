package org.subhankar.authenticationservice.service;


import org.subhankar.commonDTO.JournalDTO;

public interface KafkaService {
    void updateTransaction(JournalDTO journalDTO);

}
