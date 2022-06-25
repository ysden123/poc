package com.stulsoft.poc.pkafka.kafka.spring;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AppConfig {
    public static final String TOPIC = "spring_topic_1";
    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(TOPIC)
//                .partitions(10)
//                .replicas(1)
                .build();
    }
}
