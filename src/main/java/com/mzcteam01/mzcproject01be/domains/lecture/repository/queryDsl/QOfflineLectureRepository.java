package com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mzcteam01.mzcproject01be.domains.lecture.entity.QOfflineLecture.offlineLecture;

@RequiredArgsConstructor
@Repository
public class QOfflineLectureRepository {
    private final JPAQueryFactory queryFactory;

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

    private OrderSpecifier<?> getCreatedOrder(
            Integer searchType,
            ComparableExpressionBase<?> createdAt
    ) {
        return (searchType == null || searchType == 1) ?
                createdAt.desc() :
                createdAt.asc();
    }
}
