package com.sample.inventory.service.impl;

import com.sample.inventory.mapper.ProductMapper;
import com.sample.inventory.model.ProductEntity;
import com.sample.inventory.repository.ProductRepository;
import com.sample.inventory.service.ProductService;
import com.sample.inventory.view.ProductCreateOrUpdateDto;
import com.sample.inventory.view.ProductDto;
import com.sample.inventory.view.ProductSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public ProductDto create(ProductCreateOrUpdateDto criteria) {

        ProductEntity entity = new ProductEntity();

        copy(criteria, entity);

        ProductEntity saved = productRepository.save(entity);

        return productMapper.transform(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getById(Long id) {

        ProductEntity entity = productRepository.getByIdAndNotDeleted(id);

        return productMapper.transform(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> search(ProductSearchDto criteria, Pageable pageable) {

        Page<ProductEntity> entities = productRepository.search(criteria, pageable);

        return productMapper.transform(entities, pageable);
    }

    @Override
    public void update(Long id, ProductCreateOrUpdateDto criteria) {

        ProductEntity entity = productRepository.getByIdAndNotDeleted(id);

        copy(criteria, entity);

        productRepository.save(entity);
    }

    @Override
    public void delete(Long id) {

        ProductEntity entity = productRepository.getByIdAndNotDeleted(id);

        entity.setDeleted(true);

        productRepository.save(entity);
    }

    //
    // private

    private void copy(ProductCreateOrUpdateDto source, ProductEntity target) {

        target.setDeleted(false);
        target.setName(source.getName());
        target.setPrice(source.getPrice());
    }
}
