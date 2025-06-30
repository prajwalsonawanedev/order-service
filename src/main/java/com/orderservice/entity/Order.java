package com.orderservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "order_tbl")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String productName;

    private Integer quantity;

    private String status;

    private Double price;
}