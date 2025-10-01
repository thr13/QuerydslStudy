package com.querydsl.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.domain.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.domain.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final JPAQueryFactory jpaQueryFactory;

    //SELECT * FROM products WHERE price >= ? AND price <= ? ORDER BY price ASC
    public List<Product> findByPriceBetween(int min, int max) {
        return jpaQueryFactory
                .selectFrom(product)
                .where(product.price.between(min, max)) //또는 where(product.price.goe(min)).and(product.price.loe(max))
                .orderBy(product.price.asc())
                .fetch();
    }

    //SELECT * FROM products WHERE name LIKE ?
    public List<Product> findByKeyword(String keyword) {
        return jpaQueryFactory
                .selectFrom(product)
                .where(product.name.contains(keyword)) // 또는 .where(product.name.like("%"+keyword+"%"))
                .fetch();
    }

    //SELECT * FROM products WHERE name LIKE ? AND price <= ?
    public List<Product> searchProducts(String productName, Integer maxPrice) {
        return jpaQueryFactory
                .selectFrom(product)
                .where(nameContains(productName), priceLoe(maxPrice))
                .fetch();
    }

    private BooleanExpression nameContains(String keyword) {
        return keyword != null ? product.name.contains(keyword) : null;
    }

    private BooleanExpression priceLoe(Integer maxPrice) {
        return maxPrice != null ? product.price.loe(maxPrice) : null;
    }

}
