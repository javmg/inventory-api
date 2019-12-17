package com.sample.inventory.view;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IdAndDatesDto {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
