package com.mzcteam01.mzcproject01be.domains.user.repository;

import com.mzcteam01.mzcproject01be.domains.user.entity.QUser;
import com.mzcteam01.mzcproject01be.domains.user.entity.QUserOrganization;
import com.mzcteam01.mzcproject01be.domains.user.entity.QUserRole;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QTeacherRepository {

    private final JPAQueryFactory query;

    // 기관별 상담 가능한 선생님 목록 조회
    // -> TEACHER 역할을 가진 사용자만 조회
    // -> OrganizationUser를 통해 기관 소속 확인
    public List<User> findTeachersByOrganizaitonId(int organizationId) {
        QUser user = QUser.user;
        QUserRole userRole = QUserRole.userRole;
        QUserOrganization userOrganization = QUserOrganization.userOrganization;

        return query
                .selectFrom(user)
                .join(user.role, userRole).fetchJoin()
                .join(userOrganization).on(userOrganization.user.eq(user))
                .where(
                        userRole.name.eq("TEACHER"),
                        userOrganization.organization.id.eq(organizationId),
                        user.deletedAt.isNull()
                )
                .orderBy(user.name.asc())
                .fetch();
    }

    // 선생님 상세 정보 조회
}
