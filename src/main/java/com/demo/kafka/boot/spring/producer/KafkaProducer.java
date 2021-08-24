package com.demo.kafka.boot.spring.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.demo.kafka.boot.spring.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${application.topic.message-topic}")
    private String topic;

    public boolean sendMessage(String message) {
    	ListenableFuture<SendResult<String, String>> msg = 
    			kafkaTemplate.send(topic, createMessage(message));
        log.info("message send");
        
        return msg.isDone();
    }

    private String createMessage(String message) {
    	try {
    		ObjectMapper mapper = new ObjectMapper();
    		Message obj = new Message(message);
    		return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
}
