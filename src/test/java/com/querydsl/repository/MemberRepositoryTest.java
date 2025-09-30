package com.querydsl.repository;

import com.querydsl.domain.Member;
import com.querydsl.domain.Team;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void before() {
        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 20);
        member1.setTeam(teamA);

        Member member2 = new Member("member2", 30);
        member2.setTeam(teamA);

        Member member3 = new Member("member3", 40);
        member3.setTeam(teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);

        em.flush();
        em.clear();
    }

    @DisplayName("아이디로 회원 객체 찾기")
    @Test
    void findByUsername() {
        Member member = memberRepository.findByUsername("member1");

        assertThat(member).isNotNull();
        assertThat(member.getUsername()).isEqualTo("member1");
        assertThat(member.getAge()).isEqualTo(20);
    }

    @DisplayName("나이로 회원 객체 찾기")
    @Test
    void findByAge() {
        List<Member> result = memberRepository.findByAge(30);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getAge()).isEqualTo(30);
    }
}