package com.querydsl.repository;

import com.querydsl.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final JPAQueryFactory jpaQueryFactory;

    //SELECT * FROM members WHERE username = ?
    public Member findByUsername(String username) {
        return jpaQueryFactory
                .selectFrom(member)
                .where(member.username.eq(username))
                .fetchOne();
    }

    //SELECT * FROM members WHERE age >= ? ORDER BY username ASC
    public List<Member> findByAge(int age) {
        return jpaQueryFactory
                .selectFrom(member)
                .where(member.age.goe(age))
                .orderBy(member.username.asc())
                .fetch();
    }

}
