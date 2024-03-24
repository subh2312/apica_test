package org.subhankar.journalservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.subhankar.commonDTO.JournalDTO;
import org.subhankar.journalservice.model.DO.JournalDO;
import org.subhankar.journalservice.repository.JournalRepository;

@Configuration
@Slf4j
public class KafkaConsumerConfig {
    private final JournalRepository journalRepository;

    public KafkaConsumerConfig(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }


    @KafkaListener(topics = "user-topic", groupId = "group_1")
    public void consumeUserTransaction(JournalDTO journalDTO) {
        log.info("Consumed message: " + journalDTO.toString());
        journalRepository.save(JournalDO.builder()
                .message(journalDTO.getMessage())
                .createdBy(journalDTO.getCreatedBy())
                .createdAt(journalDTO.getCreatedAt())
                .role(journalDTO.getRole())
                .build());

    }

    @KafkaListener(topics = "auth-topic", groupId = "group_2")
    public void consumeAuthTransaction(String topic, String message, String timestamp) {
        log.info("Consumed message: " + message + " from topic: " + topic + " at: " + timestamp);
    }
}
