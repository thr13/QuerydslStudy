package com.querydsl.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.domain.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.domain.QMember.member;
import static com.querydsl.domain.QOrder.order;
import static com.querydsl.domain.QProduct.product;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final JPAQueryFactory jpaQueryFactory;

    //SELECT o.* FROM orders o JOIN members m ON o.member_id = m.id WHERE m.username = ?
    public List<Order> findByMemberUsername(String username) {
        return jpaQueryFactory
                .selectFrom(order)
                .join(order.member, member)
                .where(member.username.eq(username))
                .fetch();
    }

    //SELECT * FROM orders WHERE city = ?
    public List<Order> findByCity(String city) {
        return jpaQueryFactory
                .selectFrom(order)
                .where(order.address.city.eq(city))
                .fetch();
    }

    //SELECT o.*, m.*, p.* FROM orders o JOIN members m ON o.member_id = m.id JOIN products p ON o.product_id = p.id
    public List<Order> findAllWithMemberAndProduct() {
        return jpaQueryFactory
                .selectFrom(order)
                .join(order.member, member).fetchJoin()
                .join(order.product, product).fetchJoin()
                .fetch();
    }

    //SELECT o.*, m.*, p.* FROM orders INNER JOIN members m ON o.member_id = m.id INNER JOIN products p ON o.product_id = p.id WHERE m.username = ? AND p.name = ?
    public List<Order> searchOrders(String username, String productName) {
        return jpaQueryFactory
                .selectFrom(order)
                .join(order.member, member).fetchJoin()
                .join(order.product, product).fetchJoin()
                .where(
                        memberName(username),
                        productName(productName)
                )
                .fetch();
    }

    private BooleanExpression memberName(String username) {
        return username != null ? member.username.eq(username) : null;
    }

    private BooleanExpression productName(String productName) {
        return productName != null ? product.name.eq(productName) : null;
    }
}
