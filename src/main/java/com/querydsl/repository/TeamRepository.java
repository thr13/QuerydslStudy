package com.querydsl.repository;

import com.querydsl.domain.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.domain.QTeam.team;

@Repository
@RequiredArgsConstructor
public class TeamRepository {

    private final JPAQueryFactory jpaQueryFactory;

    //SELECT * FROM TEAM WHERE name = ? ORDER BY id ASC
    public List<Team> findByName(String name) {
        return jpaQueryFactory
                .selectFrom(team)
                .where(team.name.eq(name))
                .orderBy(team.id.asc())
                .fetch();
    }

}
