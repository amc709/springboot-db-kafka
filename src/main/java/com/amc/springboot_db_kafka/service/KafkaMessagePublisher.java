package com.amc.springboot_db_kafka.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.amc.springboot_db_kafka.constant.AppConstants;

@Service
public class KafkaMessagePublisher {

	@Autowired
	private KafkaTemplate<String, Object> template;

	@Autowired
	private @Qualifier("logService") RestClient restClient;

	//@formatter:off

	public void sendMessageToTopic(String message) {
		CompletableFuture<SendResult<String, Object>> future = template.send(AppConstants.TOPIC_AMC_TOPIC, message);
		future.whenComplete((result, err) ->{
			if (err == null) {
				String msg = String.format(
						"Message sent[%s] from partition %s with offset %s", 
						message,
						result.getRecordMetadata().partition(), 
						result.getRecordMetadata().offset()
					);
				log(msg);
			} else {
				log("Unable to send message:  " + err.getMessage());
			}
		});
	}

	
	// This calls the module that works with a Kafka topic to consolidate logging.
	public void log(String message) {
		restClient.put().uri(uriBuilder -> 
			uriBuilder
				.path("/log")
				.queryParam("msg", message)
				.build()
			)
			.retrieve()
			.toBodilessEntity();
	}

}
