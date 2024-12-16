package com.example.OrderService.controller;

import com.example.OrderService.kafka.KafkaProducer;
import com.example.OrderService.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.models.OrderEvent;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping
    public void createOrder(@RequestBody Order order) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setProduct(order.getProduct());
        orderEvent.setQuantity(order.getQuantity());
        kafkaProducer.sendOrderEvent(orderEvent);
    }
}