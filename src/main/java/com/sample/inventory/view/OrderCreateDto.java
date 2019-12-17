package com.sample.inventory.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class OrderCreateDto {

    @NotBlank
    @Email
    @Size(max = 256)
    private String buyerEmail;

    @NotEmpty
    @Valid
    private List<OrderItemCreateDto> items;
}
