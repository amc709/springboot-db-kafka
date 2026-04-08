package com.amc.springboot_db_kafka.config;

import static com.amc.springboot_db_kafka.constant.AppConstants.TOPIC_AMC_TOPIC;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.client.RestClient;

@Configuration
public class KafkaConfig {

	@Value(value = "${log.base.url}")
	private String logBaseUrl;

    @Bean
	public NewTopic amcTopic() {
		return TopicBuilder.name(TOPIC_AMC_TOPIC).build();
	}

	@Bean
	@Qualifier("logService")
	public RestClient restClient() {
		System.out.println("baseUrl:  " + logBaseUrl);
		return RestClient.builder().baseUrl(logBaseUrl).build();
	}

}
