package com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl;


import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.organization.entity.QOrganization;
import com.mzcteam01.mzcproject01be.domains.user.entity.QUser;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.mzcteam01.mzcproject01be.domains.lecture.entity.QOfflineLecture.offlineLecture;

@RequiredArgsConstructor
@Repository
public class QOfflineLectureRepository {
    private final JPAQueryFactory queryFactory;
    QOrganization organization = QOrganization.organization;
    QUser user = QUser.user;

    public Optional<OfflineLecture> findById(int id){
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(offlineLecture)
                        .join(offlineLecture.teacher, user).fetchJoin()
                        .join(offlineLecture.organization, organization).fetchJoin()
                        .where(
                                offlineLecture.id.eq(id)
                        )
                        .fetchOne()
        );
    }


    public Page<OfflineLecture> findOfflineLectures(Integer SearchType, Pageable pageable, String keyword) {

        List<OfflineLecture> offline  = queryFactory
                .selectFrom(offlineLecture)
                .where(keywordContains(keyword))
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

    public List<String> findOfflineLectureTeacherById(int teacherId) {
        return queryFactory
                .select(offlineLecture.name)
                .from(offlineLecture)
                .where(offlineLecture.teacher.id.eq(teacherId))
                .fetch();
    }

    private OrderSpecifier<?> getCreatedOrder(
            Integer searchType,
            ComparableExpressionBase<?> createdAt
    ) {
        return (searchType == null || searchType == 1) ?
                createdAt.desc() :
                createdAt.asc();
    }
    private BooleanExpression keywordContains(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return null;
        }
        return offlineLecture.name.containsIgnoreCase(keyword);

    }

}
