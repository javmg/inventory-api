package com.sample.inventory.mapper;

import com.sample.inventory.model.OrderEntity;
import com.sample.inventory.view.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper extends IdAndDatesMapper<OrderEntity, OrderDto> {

    private final OrderItemMapper orderItemMapper;

    public OrderDto transform(OrderEntity entity) {

        OrderDto dto = new OrderDto();

        super.copy(entity, dto);

        dto.setBuyerEmail(entity.getBuyerEmail());
        dto.setItems(orderItemMapper.transform(entity.getItems()));
        dto.setPrice(entity.getPrice());

        return dto;
    }
}
