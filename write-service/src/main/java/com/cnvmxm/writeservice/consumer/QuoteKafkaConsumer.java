package com.cnvmxm.writeservice.consumer;

import com.cnvmxm.writeservice.model.Quote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class QuoteKafkaConsumer implements AcknowledgingMessageListener<Long, Quote> {

    @RetryableTopic(attempts = "4") // 3 topic N-1
    @KafkaListener(
            topics = "quote.topic",
            groupId = "cnvmxm.group"
    )
    public void onMessage(
            ConsumerRecord<Long, Quote> record,
            Acknowledgment acknowledgment
    ) {
        try {
            log.info(
                    "Received: {} from {} offset {}",
                    new ObjectMapper().writeValueAsString(record.value()),
                    record.topic(),
                    record.offset()
            );
            acknowledgment.acknowledge();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
