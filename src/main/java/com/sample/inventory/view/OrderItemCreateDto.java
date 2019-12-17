package com.sample.inventory.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderItemCreateDto {

    @NotNull
    private Long product;

    @NotNull
    @Min(1)
    private Integer quantity;
}
