package com.querydsl.repository;

import com.querydsl.domain.Product;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager em;

    @Test
    void findByPriceBetween() {
        em.persist(new Product("itemA", 10000, 10));
        em.persist(new Product("itemB", 20000, 10));
        em.persist(new Product("itemC", 30000, 10));
        em.flush();
        em.clear();

        List<Product> result = productRepository.findByPriceBetween(10000, 20000);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getPrice()).isEqualTo(10000);
    }
}