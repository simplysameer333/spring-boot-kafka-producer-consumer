package com.demo.kafka.boot.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.demo.kafka.boot.spring.producer.KafkaProducer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {

	@Autowired
	private KafkaProducer producer;

	@GetMapping(value = { "api/publish/{msg}" })
	public boolean publish(@PathVariable final String msg) {
		log.info(msg + " recieved in contrller");
		boolean message = producer.sendMessage(msg);
		return message;
	}

}
