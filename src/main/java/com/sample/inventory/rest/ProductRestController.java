package com.sample.inventory.rest;

import com.sample.inventory.service.ProductService;
import com.sample.inventory.view.ProductCreateOrUpdateDto;
import com.sample.inventory.view.ProductDto;
import com.sample.inventory.view.ProductSearchDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Api(tags = "Products")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private final ProductService productService;

    @ApiOperation(value = "Create a new product.")
    @PostMapping
    @ResponseStatus(CREATED)
    public ProductDto create(@Validated @RequestBody ProductCreateOrUpdateDto criteria) {
        return productService.create(criteria);
    }

    @ApiOperation(value = "Search for products (paged).")
    @GetMapping("/paged")
    public Page<ProductDto> search(@Validated @ModelAttribute ProductSearchDto criteria, Pageable page) {
        return productService.search(criteria, page);
    }

    @ApiOperation(value = "Get the product with the given id.")
    @GetMapping(value = "/{id}")
    public ProductDto getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @ApiOperation(value = "Update the product with the given id")
    @PutMapping(value = "/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Long id, @Validated @RequestBody ProductCreateOrUpdateDto criteria) {
        productService.update(id, criteria);
    }

    @ApiOperation(value = "Delete the product with the given id.")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
