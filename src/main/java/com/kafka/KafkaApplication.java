package com.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.profiles.active:admin}")
    private String activeProfile;

    // @Autowired
    // private KafkaAdminService kafkaAdminService;

    @Override
    public void run(String... args) {
        System.out.println("Application started with profile: " + activeProfile);
        System.out.println("Connected to Kafka Bootstrap Servers: " + bootstrapServers);
        // if (activeProfile.equals("admin")) {
        //     kafkaAdminService.createTopic();
        // }
    }
}
