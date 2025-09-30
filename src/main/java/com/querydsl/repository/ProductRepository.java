package com.querydsl.repository;

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

}
