package com.querydsl.repository;

import com.querydsl.domain.Product;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void before() {
        em.persist(new Product("itemA", 10000, 10));
        em.persist(new Product("itemB", 20000, 20));
        em.persist(new Product("itemC", 30000, 5));
        em.flush();
        em.clear();
    }

    @DisplayName("특정 가격 내 상품 목록 조회")
    @Test
    void findByPriceBetween() {
        List<Product> result = productRepository.findByPriceBetween(10000, 20000);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getPrice()).isEqualTo(10000);
    }

    @DisplayName("특정 키워드로 상품 목록 조회")
    @Test
    void findByKeyword() {
        String keyword = "B";

        List<Product> result = productRepository.findByKeyword(keyword);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("itemB");
    }

    @DisplayName("이름과 최대 가격으로 상품 목록 검색")
    @Test
    void searchProductsWithAllConditions() {
        String keyword = "item";
        Integer maxPrice = 20000;

        List<Product> result = productRepository.searchProducts(keyword, maxPrice);

        assertThat(result).hasSize(2);
    }

    @DisplayName("조건이 없을 때 상품 목록 검색 시도")
    @Test
    void searchProductsWithNoConditions() {
        List<Product> result = productRepository.searchProducts(null, null);

        assertThat(result).hasSize(3);
    }

    @DisplayName("이름만으로 상품 목록 검색")
    @Test
    void searchProductsWithNameConditionOnly() {
        String keyword = "item";

        List<Product> result = productRepository.searchProducts(keyword, null);

        assertThat(result).hasSize(3);
    }

    @DisplayName("최대 가격만으로 상품 목록 검색")
    @Test
    void searchProductsWithPriceConditionOnly() {
        Integer maxPrice = 20000;

        List<Product> result = productRepository.searchProducts(null, maxPrice);

        assertThat(result).hasSize(2);
    }

}