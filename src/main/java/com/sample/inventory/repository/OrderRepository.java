package com.sample.inventory.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.sample.inventory.exception.NotFoundException;
import com.sample.inventory.model.OrderEntity;
import com.sample.inventory.model.QOrderEntity;
import com.sample.inventory.view.OrderSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;

import static java.util.Optional.ofNullable;

public interface OrderRepository extends IdAndDatesRepository<OrderEntity> {

    QOrderEntity Q_ORDER_ENTITY = QOrderEntity.orderEntity;

    @Override
    @EntityGraph(attributePaths = {"items", "items.product"})
    Page<OrderEntity> findAll(Predicate predicate, Pageable page);

    //
    // default

    @Override
    default NotFoundException notFoundException(Long id) {
        return new NotFoundException(String.format("Order with id '%s' not found.", id));
    }

    default Page<OrderEntity> search(OrderSearchDto criteria, Pageable pageable) {

        BooleanBuilder predicate = new BooleanBuilder();

        ofNullable(criteria.getCreatedAtGoe()).ifPresent(createdAt ->
                predicate.and(Q_ORDER_ENTITY.createdAt.goe(createdAt))
        );

        ofNullable(criteria.getCreatedAtLoe()).ifPresent(createdAt ->
                predicate.and(Q_ORDER_ENTITY.createdAt.loe(createdAt))
        );

        return findAll(predicate, pageable);
    }
}
