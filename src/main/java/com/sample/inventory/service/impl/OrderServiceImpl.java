package com.sample.inventory.service.impl;

import com.sample.inventory.mapper.OrderMapper;
import com.sample.inventory.model.OrderEntity;
import com.sample.inventory.model.OrderItemEntity;
import com.sample.inventory.model.ProductEntity;
import com.sample.inventory.repository.OrderRepository;
import com.sample.inventory.repository.ProductRepository;
import com.sample.inventory.service.OrderService;
import com.sample.inventory.view.OrderCreateDto;
import com.sample.inventory.view.OrderDto;
import com.sample.inventory.view.OrderItemCreateDto;
import com.sample.inventory.view.OrderSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;

    @Override
    public OrderDto create(OrderCreateDto criteria) {

        Set<Long> productIds = criteria.getItems().stream()
                .map(OrderItemCreateDto::getProduct)
                .collect(Collectors.toSet());

        Map<Long, ProductEntity> mapProductIdAndProduct = productRepository.getAllByIdAndNotDeletedAsMap(productIds);

        OrderEntity entity = new OrderEntity();

        entity.setBuyerEmail(criteria.getBuyerEmail().toLowerCase());
        entity.setPrice(ZERO);

        criteria.getItems().forEach(itemDto -> {

            OrderItemEntity itemEntity = new OrderItemEntity();

            ProductEntity product = mapProductIdAndProduct.get(itemDto.getProduct());

            itemEntity.setOrder(entity);
            itemEntity.setPrice(product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
            itemEntity.setProduct(product);
            itemEntity.setQuantity(itemDto.getQuantity());

            entity.getItems().add(itemEntity);

            entity.setPrice(entity.getPrice().add(itemEntity.getPrice()));
        });

        OrderEntity saved = orderRepository.save(entity);

        return orderMapper.transform(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> search(OrderSearchDto criteria, Pageable pageable) {

        Page<OrderEntity> entities = orderRepository.search(criteria, pageable);

        return orderMapper.transform(entities, pageable);
    }
}
