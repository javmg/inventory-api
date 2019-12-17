package com.sample.inventory.rest;

import com.sample.inventory.service.OrderService;
import com.sample.inventory.view.OrderCreateDto;
import com.sample.inventory.view.OrderDto;
import com.sample.inventory.view.OrderSearchDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Api(tags = "Orders")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderRestController {

    private final OrderService orderService;

    @ApiOperation(value = "Create a new order.")
    @PostMapping
    @ResponseStatus(CREATED)
    public OrderDto create(@Validated @RequestBody OrderCreateDto criteria) {
        return orderService.create(criteria);
    }

    @ApiOperation(value = "Search for orders (paged).")
    @GetMapping("/paged")
    public Page<OrderDto> search(@Validated @ModelAttribute OrderSearchDto criteria, Pageable page) {
        return orderService.search(criteria, page);
    }
}
