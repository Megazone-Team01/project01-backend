package com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.mzcteam01.mzcproject01be.domains.lecture.entity.QOfflineLecture.*;
import static com.mzcteam01.mzcproject01be.domains.lecture.entity.QOnlineLecture.*;

@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<OfflineLecture> findOfflineLectures(Integer SearchType, Pageable pageable) {

        List<OfflineLecture> offline  = queryFactory
                .selectFrom(offlineLecture)
                .orderBy(getCreatedOrder(SearchType, offlineLecture.createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(offlineLecture.count())
                .from(offlineLecture)
                .fetchOne();

        return new PageImpl<>(offline, pageable, total == null ? 0 : total);

    }

    @Override
    public Page<OnlineLecture> findOnlineLectures(Integer SearchType, Pageable pageable) {
        List<OnlineLecture> online = queryFactory
                .selectFrom(onlineLecture)
                .orderBy(getCreatedOrder(SearchType, onlineLecture.createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(onlineLecture.count())
                .from(onlineLecture).fetchOne();

        return new PageImpl<>(online, pageable, total == null ? 0L : total);
    }


    private OrderSpecifier<?> getCreatedOrder(
            Integer searchType,
            ComparableExpressionBase<?> createdAt
            ) {
        return (searchType == null || searchType == 1) ?
               createdAt.desc() :
                createdAt.asc();
    }





}
