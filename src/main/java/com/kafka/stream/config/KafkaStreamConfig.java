package com.kafka.stream.config;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import com.kafka.stream.service.KafkaStreamService;

@Profile("stream")
@EnableKafkaStreams
@Configuration
public class KafkaStreamConfig {

    private final KafkaStreamService kafkaStreamService;

    @Value("${spring.kafka.streams.input-topic}")
    String inputTopic;

    @Value("${spring.kafka.streams.output-topic}")
    String outputTopic;

    public KafkaStreamConfig(KafkaStreamService kafkaStreamService) {
        this.kafkaStreamService = kafkaStreamService;
    }

    @Bean
    public KStream<String, String> kStream(StreamsBuilder builder) {
        KStream<String, String> stream = builder.stream(inputTopic);

        KStream<String, String> processedStream = kafkaStreamService.processStream(stream);
        processedStream.to(outputTopic);
        return stream;
    }
}
