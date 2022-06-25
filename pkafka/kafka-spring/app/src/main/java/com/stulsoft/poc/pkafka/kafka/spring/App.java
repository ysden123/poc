package com.stulsoft.poc.pkafka.kafka.spring;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import static com.stulsoft.poc.pkafka.kafka.spring.AppConfig.TOPIC;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        return args -> {
            for (int i = 1; i <= 10; ++i) {
                template.send(TOPIC, "testKey1", "test data " + i);
                try{
                    Thread.sleep(500);
                }catch (Exception ignore){

                }
            }
        };
    }

    @KafkaListener(id = "springId", topics = TOPIC, groupId = "springGroupId")
    public void listen(ConsumerRecord<String,String> record) {
        System.out.printf("key: %s, value: %s%n", record.key(), record.value());
    }
}
