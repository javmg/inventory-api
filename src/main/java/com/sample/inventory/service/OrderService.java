package com.sample.inventory.service;

import com.sample.inventory.view.OrderCreateDto;
import com.sample.inventory.view.OrderDto;
import com.sample.inventory.view.OrderSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDto create(OrderCreateDto criteria);

    Page<OrderDto> search(OrderSearchDto criteria, Pageable pageable);
}
