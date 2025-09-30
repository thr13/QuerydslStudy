package com.querydsl.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    private String name;

    @Setter
    private int price;

    @Setter
    private int stockAmount;

    @OneToMany(mappedBy = "product")
    private List<Order> orders = new ArrayList<>();

    public Product(String name, int price, int stockAmount) {
        this.name = name;
        this.price = price;
        this.stockAmount = stockAmount;
    }
}
