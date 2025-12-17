package com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl;

import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import com.mzcteam01.mzcproject01be.domains.organization.entity.QOrganization;
import com.mzcteam01.mzcproject01be.domains.user.entity.QUser;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.mzcteam01.mzcproject01be.domains.lecture.entity.QOnlineLecture.onlineLecture;

@RequiredArgsConstructor
@Repository
public class QOnlineLectureRepository {
    private final JPAQueryFactory queryFactory;
    QOrganization organization = QOrganization.organization;
    QUser user = QUser.user;

    public Optional<OnlineLecture> findById(int id){
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(onlineLecture)
                        .join(onlineLecture.teacher, user).fetchJoin()
                        .join(onlineLecture.organization, organization).fetchJoin()
                        .where(
                                onlineLecture.id.eq(id)
                        )
                        .fetchOne()
        );
    }

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

    public List<String> findOnlineLectureNamesByTeacherId(int teacherId) {
        return queryFactory
                .select(onlineLecture.name)
                .from(onlineLecture)
                .where(onlineLecture.teacher.id.eq(teacherId))
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
}
