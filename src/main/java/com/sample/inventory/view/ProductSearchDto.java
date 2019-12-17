package com.sample.inventory.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductSearchDto  {

    private String name;

    private BigDecimal priceGoe;

    private BigDecimal priceLoe;
}
