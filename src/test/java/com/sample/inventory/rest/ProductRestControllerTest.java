package com.sample.inventory.rest;

import com.sample.inventory.AbstractRestControllerTest;
import com.sample.inventory.model.ProductEntity;
import com.sample.inventory.repository.ProductRepository;
import com.sample.inventory.util.JsonUtils;
import com.sample.inventory.view.ProductCreateOrUpdateDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Map;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.*;

/**
 * Testing for {@link ProductRestController}
 */
@Sql("/sql/product.sql")
public class ProductRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateKoBadRequest() {

        postCheckStatusAndReturnBody("/api/v1/products", new ProductCreateOrUpdateDto(), BAD_REQUEST);
    }

    @Test
    public void testCreateOk() {

        ProductCreateOrUpdateDto criteria = productCreateOrUpdateDto();

        String body = postCheckStatusAndReturnBody("/api/v1/products", criteria, CREATED);

        assertThat(body, hasJsonPath("$.id"));

        Long id = ((Number) JsonUtils.read(body, Map.class).get("id")).longValue();

        ProductEntity entity = productRepository.getById(id);

        assertThat(entity, allOf(
                hasProperty("id", is(id)),
                hasProperty("createdAt", is(notNullValue())),
                hasProperty("updatedAt", is(notNullValue())),
                hasProperty("deleted", is(false)),
                hasProperty("name", is(criteria.getName())),
                hasProperty("price", is(criteria.getPrice()))
        ));
    }

    @Test
    public void testGetByIdKoNotFoundDoesNotExist() {

        String body = getCheckStatusAndReturnBody("/api/v1/products/12345", NOT_FOUND);

        assertThat(body, allOf(
                hasJsonPath("$.message", is("Product with id '12345' not found."))
        ));
    }

    @Test
    public void testGetByIdKoNotFoundDeleted() {
        
        String body = getCheckStatusAndReturnBody("/api/v1/products/4", NOT_FOUND);

        assertThat(body, allOf(
                hasJsonPath("$.message", is("Product with id '4' not found."))
        ));
    }

    @Test
    public void testSearchOkFullMatch() {

        String body = getCheckStatusAndReturnBody("/api/v1/products/paged?name=3&priceGoe=2&priceLoe=4", OK);

        assertThat(body, allOf(
                hasJsonPath("$.content[0].id", is(3)),
                hasJsonPath("$.totalPages", is(1)),
                hasJsonPath("$.totalElements", is(1))
        ));
    }

    @Test
    public void testSearchOkOrder() {

        String body = getCheckStatusAndReturnBody("/api/v1/products/paged?sort=price,desc", OK);

        assertThat(body, allOf(
                hasJsonPath("$.content[0].id", is(3)),
                hasJsonPath("$.content[1].id", is(2)),
                hasJsonPath("$.content[2].id", is(1))
        ));
    }

    @Test
    public void testUpdateKoNotFoundDoesNotExist() {

        putCheckStatusAndReturnBody("/api/v1/products/12345", productCreateOrUpdateDto(), NOT_FOUND);
    }

    @Test
    public void testUpdateKoNotFoundDeleted() {

        putCheckStatusAndReturnBody("/api/v1/products/4", productCreateOrUpdateDto(), NOT_FOUND);
    }

    @Test
    public void testUpdateOk() {

        ProductCreateOrUpdateDto criteria = productCreateOrUpdateDto();

        putCheckStatusAndReturnBody("/api/v1/products/1", criteria, NO_CONTENT);

        ProductEntity entity = productRepository.getById(1L);

        assertThat(entity, allOf(
                hasProperty("id", is(1L)),
                hasProperty("updatedAt", is(notNullValue())),
                hasProperty("deleted", is(false)),
                hasProperty("name", is(criteria.getName())),
                hasProperty("price", is(criteria.getPrice()))
        ));
    }

    @Test
    public void testDeleteKoNotFoundDoesNotExist() {

        deleteCheckStatusAndReturnBody("/api/v1/products/12345", NOT_FOUND);
    }

    @Test
    public void testDeleteKoNotFoundDeleted() {

        deleteCheckStatusAndReturnBody("/api/v1/products/4", NOT_FOUND);
    }

    @Test
    public void testDeleteOk() {

        deleteCheckStatusAndReturnBody("/api/v1/products/1", NO_CONTENT);

        ProductEntity entity = productRepository.getById(1L);

        assertThat(entity,
                hasProperty("deleted", is(true))
        );
    }

    //
    // private

    private ProductCreateOrUpdateDto productCreateOrUpdateDto() {

        ProductCreateOrUpdateDto dto = new ProductCreateOrUpdateDto();

        dto.setName("myName");
        dto.setPrice(BigDecimal.valueOf(12.34));

        return dto;
    }


}
