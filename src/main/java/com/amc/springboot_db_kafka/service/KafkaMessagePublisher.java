package com.amc.springboot_db_kafka.service;

import java.util.concurrent.CompletableFuture;

import com.amc.springboot_db_kafka.constant.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessagePublisher {

	@Autowired
	private KafkaTemplate<String, Object> template;

	public void sendMessageToTopic(String message) {
		CompletableFuture<SendResult<String, Object>> future = template.send(AppConstants.TOPIC_AMC_TOPIC, message);
		future.whenComplete((result, err) ->{
			if (err == null) {
				String msg = String.format("Message sent[%s] from partition %s with offset %s", message,
						result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
				System.out.println(msg);
			} else {
				System.out.println("Unable to send message:  " + err.getMessage());
			}
		});
	}

}
