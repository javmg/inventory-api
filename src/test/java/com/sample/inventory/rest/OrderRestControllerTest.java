package com.sample.inventory.rest;

import com.sample.inventory.AbstractRestControllerTest;
import com.sample.inventory.model.OrderEntity;
import com.sample.inventory.repository.OrderRepository;
import com.sample.inventory.util.JsonUtils;
import com.sample.inventory.view.OrderCreateDto;
import com.sample.inventory.view.OrderItemCreateDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Map;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.*;

/**
 * Testing for {@link OrderRestController}
 */
@Sql("/sql/order.sql")
public class OrderRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testCreateKoBadRequest() {

        postCheckStatusAndReturnBody("/api/v1/orders", new OrderCreateDto(), BAD_REQUEST);
    }

    @Test
    public void testCreateOk() {

        OrderCreateDto criteria = orderCreateDto();

        String body = postCheckStatusAndReturnBody("/api/v1/orders", criteria, CREATED);

        assertThat(body, hasJsonPath("$.id"));

        Long id = ((Number) JsonUtils.read(body, Map.class).get("id")).longValue();

        OrderEntity entity = orderRepository.getById(id);

        assertThat(entity, allOf(
                hasProperty("id", is(id)),
                hasProperty("createdAt", is(notNullValue())),
                hasProperty("updatedAt", is(notNullValue())),
                hasProperty("buyerEmail", is(criteria.getBuyerEmail().toLowerCase())),
                hasProperty("items", containsInAnyOrder(
                        allOf(
                                hasProperty("id", is(notNullValue())),
                                hasProperty("createdAt", is(notNullValue())),
                                hasProperty("updatedAt", is(notNullValue())),
                                hasProperty("price", is(BigDecimal.valueOf(5).setScale(2))),
                                hasProperty("product", hasProperty("id", is(1L))),
                                hasProperty("quantity", is(5))
                        ),
                        allOf(
                                hasProperty("id", is(notNullValue())),
                                hasProperty("createdAt", is(notNullValue())),
                                hasProperty("updatedAt", is(notNullValue())),
                                hasProperty("price", is(BigDecimal.valueOf(6).setScale(2))),
                                hasProperty("product", hasProperty("id", is(2L))),
                                hasProperty("quantity", is(3))
                        )
                )),
                hasProperty("price", is(BigDecimal.valueOf(11).setScale(2)))
        ));
    }

    @Test
    public void testSearchOkFullMatch() {

        String body = getCheckStatusAndReturnBody("/api/v1/orders/paged?createdAtGoe=2019-12-02T00:00:00&createdAtLoe=2019-12-02T00:00:00", OK);

        assertThat(body, allOf(
                hasJsonPath("$.content[0].id", is(2)),
                hasJsonPath("$.content[0].items[0].id", is(2)),
                hasJsonPath("$.totalPages", is(1)),
                hasJsonPath("$.totalElements", is(1))
        ));
    }

    //
    // private

    private OrderCreateDto orderCreateDto() {

        OrderCreateDto dto = new OrderCreateDto();

        dto.setBuyerEmail("myBuyer@email.com");
        dto.setItems(asList(
                orderItemCreateDto(1L, 5),
                orderItemCreateDto(2L, 3)
        ));

        return dto;
    }

    private OrderItemCreateDto orderItemCreateDto(Long product, Integer quantity) {

        OrderItemCreateDto dto = new OrderItemCreateDto();

        dto.setProduct(product);
        dto.setQuantity(quantity);

        return dto;
    }
}
