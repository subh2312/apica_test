package org.subhankar.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.subhankar.commonDTO.JournalDTO;
import org.subhankar.authenticationservice.service.KafkaService;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {
    private final KafkaTemplate<String, JournalDTO> kafkaTemplate;

    @Override
    public void updateTransaction(JournalDTO journalDTO) {
        this.kafkaTemplate.send("auth-topic", journalDTO);
        log.info("Message sent to Auth Kafka Topic : "+journalDTO.toString());
    }
}
