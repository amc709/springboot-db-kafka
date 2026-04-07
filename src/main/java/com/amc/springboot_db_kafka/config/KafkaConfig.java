package com.amc.springboot_db_kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.amc.springboot_db_kafka.constant.AppConstants.TOPIC_AMC_TOPIC;

@Configuration
public class KafkaConfig {


    @Bean
	public NewTopic amcTopic() {
		return TopicBuilder.name(TOPIC_AMC_TOPIC).build();
	}
}
