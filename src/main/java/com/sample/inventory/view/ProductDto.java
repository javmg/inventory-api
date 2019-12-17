package com.sample.inventory.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto extends IdAndDatesDto {

    private String name;

    private BigDecimal price;
}
