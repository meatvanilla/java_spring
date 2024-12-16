package com.example.OrderStatusService.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.models.OrderEvent;

import java.time.Instant;

@Component
public class OrderStatusListener {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private static final String ORDER_STATUS_TOPIC = "order-status-topic";

    @KafkaListener(topics = "order-topic", groupId = "order_group")
    public void listen(OrderEvent orderEvent) {
        System.out.println("Order event received: " + orderEvent);
        // Отправка нового события в order-status-topic
        OrderEvent statusEvent = new OrderEvent(orderEvent.getProduct(), orderEvent.getQuantity(), "CREATED", Instant.now());
        //OrderEvent statusEvent = new OrderEvent(orderEvent.getProduct(), orderEvent.getQuantity());
        kafkaTemplate.send(ORDER_STATUS_TOPIC, statusEvent);
    }
}
