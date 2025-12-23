package com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl;


import com.mzcteam01.mzcproject01be.domains.file.entity.QFile;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.UserStatusResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.organization.entity.QOrganization;
import com.mzcteam01.mzcproject01be.domains.user.entity.QUser;
import com.mzcteam01.mzcproject01be.domains.user.entity.QUserLecture;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.NumberExpression;
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
    QUserLecture userLecture = QUserLecture.userLecture;
    QFile file = QFile.file;

    public UserStatusResponse status(int userId){
        return queryFactory
                .select(Projections.constructor(UserStatusResponse.class, organization.status))
                .from(user)
                .join(organization).on(user.id.eq(organization.owner.id))
                .fetchFirst();
    }

    public Optional<OfflineLecture> findById(int id){
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(offlineLecture)
                        .join(offlineLecture.thumbnailFile, file).fetchJoin()
                        .join(offlineLecture.teacher, user).fetchJoin()
                        .join(offlineLecture.organization, organization).fetchJoin()
                        .where(
                                offlineLecture.id.eq(id)
                        )
                        .fetchOne()
        );
    }



    public Page<OfflineLecture> findOfflineLectures(
            Integer searchType,
            Pageable pageable,
            String keyword
    ) {
        List<OfflineLecture> offline;

        if (searchType != null && searchType == 3) {
            // 인기순: Join + GroupBy 사용
            offline = queryFactory
                    .select(offlineLecture)
                    .join(offlineLecture.thumbnailFile, file).fetchJoin()
                    .from(offlineLecture)
                    .leftJoin(userLecture)
                    .on(userLecture.lectureId.eq(offlineLecture.id).and(userLecture.isOnline.eq(0)))
                    .where(
                            keywordContains(keyword)
                    )
                    .groupBy(offlineLecture.id)
                    .orderBy(userLecture.count().desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        } else {
            // 날짜순
            offline = queryFactory
                    .selectFrom(offlineLecture)
                    .join(offlineLecture.thumbnailFile, file).fetchJoin()
                    .where(
                            keywordContains(keyword)
                    )
                    .orderBy(getCreatedOrder(searchType, offlineLecture.startAt))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }

        Long total = queryFactory
                .select(offlineLecture.count())
                .from(offlineLecture)
                .where(
                        keywordContains(keyword)
                )
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

    private OrderSpecifier<?> getOrderSpecifier(
            Integer searchType,
            ComparableExpressionBase<?> startAt,
            NumberExpression<Long> registrationCount
    ) {
        if (searchType == null || searchType == 1) {
            return startAt.asc();  // 최신순
        } else if (searchType == 2) {
            return startAt.desc();   // 오래된순
        } else if (searchType == 3) {
            return registrationCount.desc();  // 인기순 (신청자 많은 순)
        }
        return startAt.desc();  // 기본값: 최신순
    }



    private BooleanExpression keywordContains(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return null;
        }
        return offlineLecture.name.containsIgnoreCase(keyword);

    }

}
