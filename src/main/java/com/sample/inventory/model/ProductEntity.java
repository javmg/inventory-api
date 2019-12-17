package com.sample.inventory.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Getter
@Setter
public class ProductEntity extends IdAndDatesEntity {

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @Column(name = "description")
    private String description;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
