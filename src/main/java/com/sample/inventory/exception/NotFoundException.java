package com.sample.inventory.exception;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
@NoArgsConstructor
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message){
        super(message);
    }
}
