package com.sample.inventory.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItemEntity extends IdAndDatesEntity {

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
