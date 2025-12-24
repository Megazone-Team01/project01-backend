package com.mzcteam01.mzcproject01be.common.base.category.repository;

import com.mzcteam01.mzcproject01be.common.base.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByCode(String code);
    List<Category> findAllByParentIdAndDeletedAtIsNull( Integer parentId );
    List<Category> findAllByParentIsNullAndDeletedAtIsNull();

    // 트리 구조용 추가 메서드
    @Query("SELECT c FROM Category c WHERE c.deletedAt IS NULL")
    List<Category> findAllNotDeleted();

    @Query("SELECT c FROM Category c WHERE c.code = :code AND c.deletedAt IS NULL")
    Optional<Category> findByCodeAndNotDeleted(@Param("code") String code);
}
