package com.springboot.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.*;

import java.util.HashMap;
import java.util.Map;


@EnableKafka
@Configuration
public class ConsumerConfiguration {


    @Bean
    public ConsumerFactory<String, String> kafkaTransConsumerFactory(){

        Map<String, Object> config = new HashMap<String, Object>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        return new DefaultKafkaConsumerFactory<String, String>(config);

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaTransKafkaListenerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(kafkaTransConsumerFactory());
       // factory.setErrorHandler(new SeekToCurrentErrorHandler());
        //factory.setAutoStartup(true);
        return factory;
    }


    @Bean
    public ConsumerFactory<String, String> esReconConsumerFactory(){

        Map<String, Object> config = new HashMap<String, Object>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
        //config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        return new DefaultKafkaConsumerFactory<String, String>(config);

    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(esReconConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}
