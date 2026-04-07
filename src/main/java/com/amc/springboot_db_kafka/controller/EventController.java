package com.amc.springboot_db_kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amc.springboot_db_kafka.service.KafkaMessagePublisher;

@RestController
@RequestMapping("/producer")
public class EventController {

	
	@Autowired
	private KafkaMessagePublisher publisher;
	
	@PutMapping("/publish")
	public ResponseEntity<?> publishMessage(@RequestParam String message) {
		try {
			this.publisher.sendMessageToTopic(message);
			return ResponseEntity.ok("Message published");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
