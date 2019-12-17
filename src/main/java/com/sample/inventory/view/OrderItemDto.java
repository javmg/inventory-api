package com.sample.inventory.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemDto extends IdAndDatesDto {

    private BigDecimal price;

    private ProductDto product;

    private Integer quantity;
}
