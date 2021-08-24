package com.demo.kafka.boot.spring.consumer;

import com.demo.kafka.boot.spring.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class KafkaConsumer {
    @KafkaListener(topics = "${application.topic.message-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(final ConsumerRecord<String, String> consumerRecord) throws IOException {
        Message message = new ObjectMapper().readValue(consumerRecord.value(), Message.class);
        log.trace("Received Messasge: " + message.toString());
    }
}