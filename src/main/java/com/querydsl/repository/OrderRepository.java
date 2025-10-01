package com.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final JPAQueryFactory jpaQueryFactory;

    //고객 이름으로 주문 조회
    //SELECT * FROM orders o join members m WHERE m.name = ?

    //주문 상품 조회
    //SELECT * FROM orders o join products p WHERE o.

    //주문 정보 조회

    //최신 주문 순 페이징

    //주문 주소 찾기
}
