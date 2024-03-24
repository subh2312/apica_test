package org.subhankar.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.subhankar.commonDTO.JournalDTO;
import org.subhankar.userservice.service.KafkaService;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {
    private final KafkaTemplate<String, JournalDTO> kafkaTemplate;

    @Override
    public void updateTransaction(JournalDTO journalDTO) {
        this.kafkaTemplate.send("user-topic", journalDTO);
        log.info("Message sent to Kafka Topic : "+journalDTO.toString());
    }
}
