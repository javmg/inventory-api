package com.sample.inventory.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductCreateOrUpdateDto {

    @NotBlank
    @Size(max = 256)
    private String name;

    @NotNull
    @Min(0)
    private BigDecimal price;
}
