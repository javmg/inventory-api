package com.sample.inventory.service;

import com.sample.inventory.view.ProductCreateOrUpdateDto;
import com.sample.inventory.view.ProductDto;
import com.sample.inventory.view.ProductSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductDto create(ProductCreateOrUpdateDto criteria);

    ProductDto getById(Long id);

    Page<ProductDto> search(ProductSearchDto criteria, Pageable pageable);

    void update(Long id, ProductCreateOrUpdateDto criteria);

    void delete(Long id);
}
