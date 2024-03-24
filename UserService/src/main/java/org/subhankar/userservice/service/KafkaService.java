package org.subhankar.userservice.service;


import org.subhankar.commonDTO.JournalDTO;

public interface KafkaService {
    void updateTransaction(JournalDTO journalDTO);

}
