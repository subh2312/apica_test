package org.subhankar.userservice.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;
import org.subhankar.commonDTO.JournalDTO;

import java.io.*;
import java.util.Map;
@Component
@Slf4j
public class JournalDTOSerializer implements Serializer<JournalDTO> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, JournalDTO journalDTO) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
            objStream.writeObject(journalDTO);
            objStream.close();
            return byteStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
