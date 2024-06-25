package com.example.demo.repository;

import com.example.demo.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class ProductCrudTest {

    @Autowired
    ProductRepository productRepository;

    @Before
    public void before() {
        Product product = new Product();
        product.setName("IPhone");
        product.setId(1L);
        productRepository.save(product);
    }

    @Test
    @Order(2)
    public void test_product_repository() {
        Product product = productRepository.findById(1L).get();
        assertEquals(product.getName(), "IPhone");
    }
}