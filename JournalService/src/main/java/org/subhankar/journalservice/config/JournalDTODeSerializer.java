package org.subhankar.journalservice.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;
import org.subhankar.commonDTO.JournalDTO;

import java.io.*;
import java.util.Map;

@Component
@Slf4j
public class JournalDTODeSerializer implements Deserializer<JournalDTO> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public JournalDTO deserialize(String s, byte[] data) {
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
            ObjectInputStream objStream = new ObjectInputStream(byteStream);
            return (JournalDTO) objStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
