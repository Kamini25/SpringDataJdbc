package com.springboot.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.model.History;
import com.springboot.repository.HistoryRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
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

    @KafkaListener(topics = topic, groupId = "group-id", containerFactory = "kafkaListenerContainerFactory")
   // @KafkaListener()
    public void consume (String t, Acknowledgment ack) throws Exception {

        logger.info("Reading from consumer " );
        ObjectMapper objectMapper = new ObjectMapper();
        History history = objectMapper.readValue(t,History.class);
        try {


            historyRepository.save(history);
            ack.acknowledge();
            //logger.info("Message read as " + history.getaction() + "     " + history.getId());
        }
        catch(Exception e) {
          //  logger.info("error occured " + e.getMessage());
            throw new Exception("Failed to persist. Reason: " + e.getMessage());
        }

    }
}
