package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private String product;
    private Integer quantity;
    private String status; // Новый статус
    private Instant date;  // Дата
}