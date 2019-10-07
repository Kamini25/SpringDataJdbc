package com.springboot.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.model.History;
import com.springboot.services.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static  final  String topic = "Paytmtopic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(History t) throws JsonProcessingException {
        logger.info("Inside send message to topic");
        ObjectMapper mapper = new ObjectMapper();
        this.kafkaTemplate.send(topic,mapper.writeValueAsString(t));

    }

}
