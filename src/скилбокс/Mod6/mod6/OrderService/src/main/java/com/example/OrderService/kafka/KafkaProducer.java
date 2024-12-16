package com.example.OrderService.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.models.OrderEvent;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private static final String ORDER_TOPIC = "order-topic";

    public void sendOrderEvent(OrderEvent orderEvent) {
        kafkaTemplate.send(ORDER_TOPIC, orderEvent);
    }
}