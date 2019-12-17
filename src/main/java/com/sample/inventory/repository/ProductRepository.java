package com.sample.inventory.repository;

import com.querydsl.core.BooleanBuilder;
import com.sample.inventory.exception.NotFoundException;
import com.sample.inventory.model.ProductEntity;
import com.sample.inventory.model.QProductEntity;
import com.sample.inventory.view.ProductSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public interface ProductRepository extends IdAndDatesRepository<ProductEntity> {

    QProductEntity Q_PRODUCT_ENTITY = com.sample.inventory.model.QProductEntity.productEntity;

    List<ProductEntity> findByIdInAndDeletedFalse(Collection<Long> ids);

    Optional<ProductEntity> findByIdAndDeletedFalse(long id);

    //
    // default

    @Override
    default NotFoundException notFoundException(Long id) {
        return new NotFoundException(String.format("Product with id '%s' not found.", id));
    }

    default ProductEntity getByIdAndNotDeleted(Long id) {
        return findByIdAndDeletedFalse(id).orElseThrow(() -> notFoundException(id));
    }

    default Map<Long, ProductEntity> getAllByIdAndNotDeletedAsMap(Collection<Long> ids) {

        if (ids.isEmpty()) {
            return Collections.emptyMap();
        }

        List<ProductEntity> results = this.findByIdInAndDeletedFalse(ids);

        Map<Long, ProductEntity> mapIdAndEntity = results.stream().collect(Collectors.toMap(ProductEntity::getId, Function.identity()));

        ids.forEach((id) -> {
            if (!mapIdAndEntity.containsKey(id)) {
                throw notFoundException(id);
            }
        });

        return mapIdAndEntity;
    }

    default Collection<ProductEntity> getAllByIdAndNotDeleted(Collection<Long> ids) {

        return getAllByIdAndNotDeletedAsMap(ids).values();
    }

    default Page<ProductEntity> search(ProductSearchDto criteria, Pageable pageable) {

        BooleanBuilder predicate = new BooleanBuilder();

        predicate.and(Q_PRODUCT_ENTITY.deleted.isFalse());

        ofNullable(criteria.getName()).ifPresent(name ->
                predicate.and(Q_PRODUCT_ENTITY.name.containsIgnoreCase(name))
        );

        ofNullable(criteria.getPriceGoe()).ifPresent(price ->
                predicate.and(Q_PRODUCT_ENTITY.price.goe(price))
        );

        ofNullable(criteria.getPriceLoe()).ifPresent(price ->
                predicate.and(Q_PRODUCT_ENTITY.price.loe(price))
        );

        return findAll(predicate, pageable);
    }
}
