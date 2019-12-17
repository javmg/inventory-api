package com.sample.inventory.mapper;

import com.sample.inventory.model.IdAndDatesEntity;
import com.sample.inventory.view.IdAndDatesDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public abstract class IdAndDatesMapper<E extends IdAndDatesEntity, D extends IdAndDatesDto> {

    public void copy(E source, D target) {

        target.setId(source.getId());
        target.setCreatedAt(source.getCreatedAt());
        target.setUpdatedAt(source.getUpdatedAt());
    }

    public abstract D transform(E source);


    public Page<D> transform(Page<E> entities, Pageable pageable) {

        return new PageImpl<>(entities.getContent().stream()
                .map(this::transform).collect(Collectors.toList()), pageable, entities.getTotalElements()
        );
    }

    public List<D> transform(Collection<E> entities) {

        return entities.stream()
                .map(this::transform)
                .collect(Collectors.toList());

    }
}
