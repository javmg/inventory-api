package com.sample.inventory.mapper;

import com.sample.inventory.model.OrderItemEntity;
import com.sample.inventory.view.OrderItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemMapper extends IdAndDatesMapper<OrderItemEntity, OrderItemDto> {

    private final ProductMapper productMapper;

    public OrderItemDto transform(OrderItemEntity entity) {

        OrderItemDto dto = new OrderItemDto();

        super.copy(entity, dto);

        dto.setPrice(entity.getPrice());
        dto.setProduct(productMapper.transform(entity.getProduct()));
        dto.setQuantity(entity.getQuantity());

        return dto;
    }
}
