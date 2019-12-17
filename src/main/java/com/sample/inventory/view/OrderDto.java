package com.sample.inventory.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderDto extends IdAndDatesDto {

    private String buyerEmail;

    private List<OrderItemDto> items;

    private BigDecimal price;
}
