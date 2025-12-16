package com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mzcteam01.mzcproject01be.domains.lecture.entity.QOnlineLecture.onlineLecture;

@RequiredArgsConstructor
@Repository
public class QOnlineLectureRepository {
    private final JPAQueryFactory queryFactory;

    public Page<OnlineLecture> findOnlineLectures(Integer SearchType, Pageable pageable) {
        List<OnlineLecture> online = queryFactory
                .selectFrom(onlineLecture)
                .orderBy(getCreatedOrder(SearchType, onlineLecture.createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(onlineLecture.count())
                .from(onlineLecture)
                .fetchOne();

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
