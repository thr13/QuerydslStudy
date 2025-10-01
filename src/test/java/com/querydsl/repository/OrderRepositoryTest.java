package com.querydsl.repository;

import com.querydsl.domain.*;
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
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void before() {
        Team teamA = new Team("TeamA");
        em.persist(teamA);

        Member memberA = new Member("memberA", 20);
        memberA.setTeam(teamA);
        em.persist(memberA);

        Member memberB = new Member("memberB", 21);
        memberB.setTeam(teamA);
        em.persist(memberB);

        Product productA = new Product("itemA", 10000, 5);
        Product productB = new Product("itemB", 25000, 3);
        Product productC = new Product("itemA", 15000, 1);
        em.persist(productA);
        em.persist(productB);
        em.persist(productC);

        Address address = new Address("광주광역시", "우치로", "77");
        Order orderA = new Order(50000, address, memberA, productA);
        Order orderB = new Order(75000, address, memberA, productB);
        Order orderC = new Order(15000, address, memberB, productC);
        em.persist(orderA);
        em.persist(orderB);
        em.persist(orderC);

        em.flush();
        em.clear();
    }

    @DisplayName("특정 회원의 아이디로 회원의 주문 목록 조회")
    @Test
    void findByMemberUsername() {
        List<Order> result = orderRepository.findByMemberUsername("memberA");

        assertThat(result).hasSize(2);
        assertThat(result).extracting("orderAmount").containsExactly(50000, 75000);
    }

    @DisplayName("특정 도시로 배송되는 주문 목록 조회")
    @Test
    void findByCityTest() {
        List<Order> result = orderRepository.findByCity("광주광역시");

        assertThat(result).hasSize(3);
        assertThat(result).extracting("member.username").containsOnly("memberA", "memberB");
    }

    @DisplayName("모든 주문 목록 조회")
    @Test
    void findAllWithMemberAndProduct() {
        List<Order> result = orderRepository.findAllWithMemberAndProduct();

        assertThat(result).hasSize(3);
    }

    @DisplayName("회원 아이디와 상품명 으로 주문 검색")
    @Test
    void searchOrders_AllConditions() {
        String username = "memberA";
        String productName = "itemA";
        List<Order> result = orderRepository.searchOrders(username, productName);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getOrderAmount()).isEqualTo(50000);
    }

    @DisplayName("회원 이름만으로 주문 검색")
    @Test
    void searchOrders_UsernameOnly() {
        String username = "memberA";
        List<Order> result = orderRepository.searchOrders(username, null);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getMember().getUsername()).isEqualTo("memberA");
    }

    @DisplayName("상품명으로만 주문 검색")
    @Test
    void searchOrders_ProductNameOnly() {
        String productName = "itemB";
        List<Order> result = orderRepository.searchOrders(null, productName);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProduct().getName()).isEqualTo("itemB");
    }

}