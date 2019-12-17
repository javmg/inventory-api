package com.sample.inventory.mapper;

import com.sample.inventory.model.ProductEntity;
import com.sample.inventory.view.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper extends IdAndDatesMapper<ProductEntity, ProductDto>{

    @Override
    public ProductDto transform(ProductEntity entity) {

        ProductDto dto = new ProductDto();

        super.copy(entity, dto);

        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());

        return dto;
    }

    public Page<ProductDto> transform(Page<ProductEntity> entities, Pageable pageable) {

        return new PageImpl<>(entities.getContent().stream()
                .map(this::transform).collect(Collectors.toList()), pageable, entities.getTotalElements()
        );
    }

    public List<ProductDto> transform(Collection<ProductEntity> entities) {

        return entities.stream()
                .map(this::transform)
                .collect(Collectors.toList());

    }

}
