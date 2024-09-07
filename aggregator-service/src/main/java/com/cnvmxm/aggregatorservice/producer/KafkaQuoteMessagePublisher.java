package com.cnvmxm.aggregatorservice.producer;

import com.cnvmxm.aggregatorservice.model.entity.Quote;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.currentThread;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaQuoteMessagePublisher {

    private final KafkaTemplate<Long, Quote> kafkaTemplate;

    @Value("${topic.name}")
    private String topicName;

    public void sendMessage(Quote quote) throws Exception {
        CompletableFuture<SendResult<Long, Quote>> sendResult = kafkaTemplate.send(topicName, quote);
        sendResult.whenComplete(
                (result, ex) -> {
                    if (ex == null) {
                        System.out.println("Send message=[" + quote.toString() +
                                "] with offset=[" + result.getRecordMetadata().offset() + "]");
                    } else {
                        System.out.println("Unable to send message=[" +
                                quote.toString() + "] due to :" + ex.getMessage());
                    }
                });
        try {
            sendResult.get();
        } catch (ExecutionException e) {
            throw new Exception(quote.getId() + " " + e.getCause());
        } catch (InterruptedException e) {
            currentThread().interrupt();
            throw new Exception("The operation was interrupted", e);
        }
    }
}
