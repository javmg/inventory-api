package com.sample.inventory.view;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Getter
@Setter
public class OrderSearchDto {

    @DateTimeFormat(iso = DATE_TIME)
    private LocalDateTime createdAtGoe;

    @DateTimeFormat(iso = DATE_TIME)
    private LocalDateTime createdAtLoe;
}
