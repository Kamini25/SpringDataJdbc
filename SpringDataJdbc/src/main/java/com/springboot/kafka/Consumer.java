package com.springboot.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.model.History;
import com.springboot.repository.HistoryRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Consumer  {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private static  final  String topic = "Paytmtopic";

    HistoryRepository historyRepository;

    public Consumer(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @KafkaListener(topics = topic, group = "group-id", containerFactory = "kafkaListenerContainerFactory")
    public void consume (String t) throws IOException {
        logger.info("Reading from consumer " );
        ObjectMapper objectMapper = new ObjectMapper();
        History history = objectMapper.readValue(t,History.class);
        historyRepository.save(history);
        logger.info("Message read as " + history.getaction()+ "     " + history.getId()) ;

    }
}
