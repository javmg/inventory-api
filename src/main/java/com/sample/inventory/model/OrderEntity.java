package com.sample.inventory.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "`order`")
@Getter
@Setter
public class OrderEntity extends IdAndDatesEntity {

    @Column(name = "buyer_email", nullable = false)
    private String buyerEmail;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItemEntity> items = new ArrayList<>();

}
