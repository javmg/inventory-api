package com.sample.inventory.repository;

import com.sample.inventory.exception.NotFoundException;
import com.sample.inventory.model.IdAndDatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IdAndDatesRepository<T extends IdAndDatesEntity> extends JpaRepository<T, Long>, QuerydslPredicateExecutor<T> {

    NotFoundException notFoundException(Long id);

    //
    // default

    default T getById(Long id) {
        return findById(id).orElseThrow(() -> notFoundException(id));
    }
}
